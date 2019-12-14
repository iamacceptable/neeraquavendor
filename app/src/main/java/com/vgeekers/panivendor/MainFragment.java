package com.vgeekers.panivendor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.vgeekers.panivendor.fragments.BaseFragment;
import com.vgeekers.panivendor.fragments.FragmentLauncher;
import com.vgeekers.panivendor.response.CategoryResponse;
import com.vgeekers.panivendor.response.VendorPreviousDetailResponse;
import com.vgeekers.panivendor.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.vgeekers.panivendor.TerminalConstant.MY_PREFS;
import static com.vgeekers.panivendor.TerminalConstant.USER_ID_KEY;

public class MainFragment extends BaseFragment {

    private TextView mToTextView;
    private TextView mFromTextView;
    private TextView mTotalOrder;
    private TextView mTotalSale;
    private TextView mFreeBottles;
    private Spinner categorySpinner;
    private String mCategorySelected = "";
    private static final String TOTAL_ORDER = "Total Order: ";
    private static final String TOTAL_SALE = "Total Sale: ₹ ";
    private static final String FREE_BOTTLES = "Free Bottles: ";
    private Activity mActivity;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        if (getActivity() != null && getContext() != null) {
            mActivity = getActivity();
            mContext = getContext();
        }
        Button editProfile = view.findViewById(R.id.mainEditProfile);
        Button mainOrders = view.findViewById(R.id.mainOrders);
        Button getDetailsButton = view.findViewById(R.id.getDetailsButton);
        mToTextView = view.findViewById(R.id.toSpinner);
        mFromTextView = view.findViewById(R.id.fromSpinner);
        mTotalOrder = view.findViewById(R.id.textView8);
        mTotalSale = view.findViewById(R.id.textView9);
        mFreeBottles = view.findViewById(R.id.textView10);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        TextView logoutText = view.findViewById(R.id.logoutText);
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mActivity.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
                editor.putBoolean(TerminalConstant.USER_LOGIN_DONE_KEY, false);
                editor.apply();
                startActivity(new Intent(getActivity(), Authentication.class));
                mActivity.finish();
            }
        });
        mToTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToTextView.setText("");
                handleToDate(mToTextView);
            }
        });
        mFromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFromTextView.setText("");
                handleFromDate(mFromTextView);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLauncher.launchFragment(mActivity, R.id.mainFrameLayout, new EditProfile(), true, false);
            }
        });
        mainOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Orders.class));
            }
        });
        handleCategorySpinner();
        getDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTotalOrder.setText(TOTAL_ORDER);
                mTotalSale.setText(TOTAL_SALE);
                getTransactionDetails();
            }
        });
        return view;
    }

    private void handleFromDate(final View view) {
        final Calendar calendar = Calendar.getInstance();
        int year;
        int monthOfYear;
        int dayOfMonth;
        if (!((TextView) view).getText().toString().equalsIgnoreCase("")) {
            String[] data = ((TextView) view).getText().toString().split("/");
            year = Integer.parseInt(data[2]);
            monthOfYear = Integer.parseInt(data[1]) - 1;
            dayOfMonth = Integer.parseInt(data[0]);
        } else {
            year = calendar.get(Calendar.YEAR);
            monthOfYear = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker pView, int pYear, int pMonth, int pDay) {
                String date = pYear + "".concat("-").concat(setCalendarPadding(pMonth + 1)).concat("-").concat(setCalendarPadding(pDay));
                ((TextView) view).setText(date);
                ((TextView) view).setError(null);
            }
        }, year, monthOfYear, dayOfMonth);
        if (datePickerDialog.getWindow() != null) {
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    private void handleToDate(final View view) {
        final Calendar calendar = Calendar.getInstance();
        int year;
        int monthOfYear;
        int dayOfMonth;
        if (!((TextView) view).getText().toString().equalsIgnoreCase("")) {
            String[] data = ((TextView) view).getText().toString().split("/");
            year = Integer.parseInt(data[2]);
            monthOfYear = Integer.parseInt(data[1]) - 1;
            dayOfMonth = Integer.parseInt(data[0]);
        } else {
            year = calendar.get(Calendar.YEAR);
            monthOfYear = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker pView, int pYear, int pMonth, int pDay) {
                String date = pYear + "".concat("-").concat(setCalendarPadding(pMonth + 1)).concat("-").concat(setCalendarPadding(pDay));
                ((TextView) view).setText(date);
                ((TextView) view).setError(null);
            }
        }, year, monthOfYear, dayOfMonth);
        if (datePickerDialog.getWindow() != null) {
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    private static String setCalendarPadding(int input) {
        String value;
        if (input < 10) {
            value = "0" + input;
        } else {
            value = String.valueOf(input);
        }
        return value;
    }

    private void handleCategorySpinner() {
        RetrofitApi.getPaniServicesObject().getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                stopProgress();
                if (response.isSuccessful()) {
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse != null && categoryResponse.getCategory() != null && !categoryResponse.getCategory().isEmpty()) {
                        final List<String> listCategory = new ArrayList<>();
                        listCategory.add("Please select category");
                        listCategory.addAll(categoryResponse.getCategory());
                        ArrayAdapter<String> dataAdapterCity = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listCategory);
                        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categorySpinner.setAdapter(dataAdapterCity);
                        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                mCategorySelected = listCategory.get(i);
                                if (mCategorySelected.toLowerCase().contains("select")) {
                                    showToast("Please select a category from drop-down");
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                mCategorySelected = "";
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                    return;
                }
                stopProgress();
            }
        });
    }

    private void getTransactionDetails() {
        SharedPreferences prefs = mActivity.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String loginId = prefs.getString(USER_ID_KEY, "");
        String to = mToTextView.getText().toString().trim();
        String from = mFromTextView.getText().toString().trim();
        if (to.isEmpty() || from.isEmpty()) {
            showToast("Please select both dates first");
            return;
        }
        showProgress();
        RetrofitApi.getPaniServicesObject().getTransactionsDetails(loginId, from, to, mCategorySelected).enqueue(new Callback<VendorPreviousDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorPreviousDetailResponse> call, @NonNull Response<VendorPreviousDetailResponse> response) {
                stopProgress();
                if (response.isSuccessful()) {
                    //todo here tou have to use the offer value
                    VendorPreviousDetailResponse detailResponse = response.body();
                    if (detailResponse != null) {
                        if (detailResponse.getErrorCode().equals(TerminalConstant.SUCCESS)) {
                            mTotalOrder.setText(TOTAL_ORDER.concat(detailResponse.getTotalOrders()));
                            mTotalSale.setText(TOTAL_SALE.concat(detailResponse.getTotalPayment()));
                            mFreeBottles.setText(FREE_BOTTLES.concat(detailResponse.getFreeBottles()));
                        } else {
                            showToast(detailResponse.getErrorMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<VendorPreviousDetailResponse> call, @NonNull Throwable t) {
                stopProgress();
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
