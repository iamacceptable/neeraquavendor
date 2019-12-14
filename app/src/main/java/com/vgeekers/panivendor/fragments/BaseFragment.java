package com.vgeekers.panivendor.fragments;

import android.app.ProgressDialog;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.vgeekers.panivendor.R;
import com.vgeekers.panivendor.response.FetchAllStatesResponse;
import com.vgeekers.panivendor.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {

    protected static List<String> sStateList = new ArrayList<>();
    protected ProgressDialog mProgressDialog;

    protected void showProgress() {
        mProgressDialog = new ProgressDialog(getContext());
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

    protected void showToast(String msg) {
        try {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            /*
             * Just to holding the crashes
             * */
        }
    }

    /*protected String sendOtpViaSms() {
        try {
            // Construct data
            String apiKey = "apikey=" + TerminalConstant.SMS_KEY;
            Random random = new SecureRandom();
            int randomNumber = random.nextInt(999999);
            String message = "&message=" + "This is your otp is ".concat(String.valueOf(randomNumber));
            String sender = "&sender=" + "NeerAqua";
            String numbers = "&numbers=" + "8934023658";
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }*/

    /*protected void getFacebookOtp() {
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            //Handle Returning User
        } else {
            //Handle new or logged out user
        }
    }*/

    protected void setupStateSpinner() {
        RetrofitApi.getPaniServicesObject().getAllStates().enqueue(new Callback<FetchAllStatesResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetchAllStatesResponse> call, @NonNull Response<FetchAllStatesResponse> response) {
                if (response.isSuccessful()) {
                    FetchAllStatesResponse statesResponse = response.body();
                    if (statesResponse != null && statesResponse.getStates() != null && !statesResponse.getStates().isEmpty() && getContext() != null) {
                        sStateList = statesResponse.getStates();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchAllStatesResponse> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
