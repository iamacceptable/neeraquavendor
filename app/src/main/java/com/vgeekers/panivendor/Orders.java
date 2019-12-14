package com.vgeekers.panivendor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vgeekers.panivendor.response.Booking;
import com.vgeekers.panivendor.response.CommonResponse;
import com.vgeekers.panivendor.response.FetchBottleBookingResponse;
import com.vgeekers.panivendor.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.List;

public class Orders extends AppCompatActivity implements Callback<FetchBottleBookingResponse> {

    private RecyclerView ordersRecycler;
    protected ProgressDialog mProgressDialog;
    private static final String ACCEPT = "accept";
    private static final String REJECT = "reject";

    protected void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Processing");
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    protected void stopProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getAllBottlesFromServerApi();
        ordersRecycler = findViewById(R.id.ordersRecycler);
    }

    private void getAllBottlesFromServerApi() {
        showProgress();
        RetrofitApi.getPaniServicesObject().getBottleRequestsList(TerminalConstant.sUserId).enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<FetchBottleBookingResponse> call, @NonNull Response<FetchBottleBookingResponse> response) {
        stopProgress();
        if (response.isSuccessful()) {
            FetchBottleBookingResponse bookingResponse = response.body();
            if (bookingResponse != null) {
                List<Booking> bookingList = bookingResponse.getBooking();
                if (!bookingList.isEmpty()) {
                    ordersRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ordersRecycler.setAdapter(new BottleAvailableAdapter(bookingList));
                } else {
                    Toast.makeText(this, "Sorry, no order available for booking", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<FetchBottleBookingResponse> call, @NonNull Throwable t) {
        stopProgress();
        if (t instanceof UnknownHostException) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Sorry, no order available for booking", Toast.LENGTH_SHORT).show();
    }

    public class BottleAvailableAdapter extends RecyclerView.Adapter<BottleAvailableAdapter.BottleAvailableViewHolder> {

        List<Booking> list;

        BottleAvailableAdapter(List<Booking> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public BottleAvailableAdapter.BottleAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.order_items, parent, false);
            return new BottleAvailableAdapter.BottleAvailableViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(@NonNull final BottleAvailableAdapter.BottleAvailableViewHolder holder, int position) {
            final Booking bottleAvailable = list.get(position);
            String date = "Date : " + bottleAvailable.getBDate();
            holder.itemDate.setText(date);
            holder.itemMobile.setText(bottleAvailable.getUMobile());
            holder.itemBrand.setText(bottleAvailable.getBBrand());
            String quantity = bottleAvailable.getQuantity().concat(" bottles");
            holder.itemQuantity.setText(quantity);
            holder.itemAddress.setText(bottleAvailable.getUHno().concat(", " + bottleAvailable.getUCity()).concat(", " + bottleAvailable.getULocality()).concat(", " + bottleAvailable.getUState()));
            Glide.with(Orders.this).load(bottleAvailable.getBImage()).placeholder(R.drawable.bottle_logo).into(holder.itemImage);
            holder.itemAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doAccept(bottleAvailable.getBId());
                }
            });
            holder.itemReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doReject(bottleAvailable.getBId());
                    startActivity(new Intent(Orders.this, RejectPage.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class BottleAvailableViewHolder extends RecyclerView.ViewHolder {

            private ImageView itemImage;
            private TextView itemDate;
            private TextView itemBrand;
            private TextView itemMobile;
            private TextView itemQuantity;
            private TextView itemAddress;
            private Button itemAccept;
            private Button itemReject;

            BottleAvailableViewHolder(View itemView) {
                super(itemView);
                itemDate = itemView.findViewById(R.id.itemDate);
                itemImage = itemView.findViewById(R.id.itemImage);
                itemMobile = itemView.findViewById(R.id.itemMobile);
                itemAddress = itemView.findViewById(R.id.itemAddress);
                itemAccept = itemView.findViewById(R.id.itemAccept);
                itemReject = itemView.findViewById(R.id.itemReject);
                itemBrand = itemView.findViewById(R.id.itemBrandName);
                itemQuantity = itemView.findViewById(R.id.itemQuantity);
            }
        }
    }

    private void doReject(String bId) {
        showProgress();
        RetrofitApi.getPaniServicesObject().doBookingConfirmation(bId, REJECT).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                stopProgress();
                if (response.isSuccessful()) {
                    CommonResponse commonResponse = response.body();
                    if (commonResponse != null) {
                        if (commonResponse.getErrorCode().equals(TerminalConstant.SUCCESS)) {
                            startActivity(new Intent(Orders.this, RejectPage.class));
                        } else {
                            Toast.makeText(Orders.this, commonResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                stopProgress();
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doAccept(String bId) {
        showProgress();
        RetrofitApi.getPaniServicesObject().doBookingConfirmation(bId, ACCEPT).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                stopProgress();
                if (response.isSuccessful()) {
                    CommonResponse commonResponse = response.body();
                    if (commonResponse != null) {
                        if (commonResponse.getErrorCode().equals(TerminalConstant.SUCCESS)) {
                            startActivity(new Intent(Orders.this, AcceptPage.class));
                        } else {
                            Toast.makeText(Orders.this, commonResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                    return;
                }
                stopProgress();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}