package com.vgeekers.panivendor;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.material.textfield.TextInputLayout;
import com.vgeekers.panivendor.response.CommonResponse;
import com.vgeekers.panivendor.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout forgotPasswordMobile;
    private TextInputLayout forgotPasswordNewPassword;
    private TextInputLayout forgotPasswordConfirmPassword;
    private Button sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordConfirmPassword = findViewById(R.id.forgotPasswrodConfirmPassword);
        forgotPasswordNewPassword = findViewById(R.id.forgotPasswordNewPassword);
        forgotPasswordMobile = findViewById(R.id.forgotPasswordMobile);
        sendOtp = findViewById(R.id.sendOtpBtn);
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validMobile() || !validPassword() || !validConfirmPassword()) {
                    return;
                }
                sendFacebookOtpForVerification();
            }
        });
    }

    private boolean validPassword() {
        String password = forgotPasswordMobile.getEditText().getText().toString();
        if (password.length() == 0) {
            forgotPasswordNewPassword.setError("Password can't be empty!!");
            return false;
        } else if (password.length() < 3) {
            forgotPasswordNewPassword.setError("Password too short!!");
            return false;
        } else if (password.length() > 12) {
            forgotPasswordNewPassword.setError("Password too long!!");
            return false;
        }
        forgotPasswordNewPassword.setError(null);
        return true;
    }

    private boolean validMobile() {
        String mobile = forgotPasswordMobile.getEditText().getText().toString();
        if (mobile.length() == 0) {
            forgotPasswordMobile.setError("Mobile Number can't be empty!!");
            return false;
        } else if (mobile.length() < 10) {
            forgotPasswordMobile.setError("Mobile Number too short!!");
            return false;
        } else if (mobile.length() > 10) {
            forgotPasswordMobile.setError("Mobile Number too long!!");
            return false;
        }
        forgotPasswordMobile.setError(null);
        return true;
    }

    private boolean validConfirmPassword() {
        String password = forgotPasswordConfirmPassword.getEditText().getText().toString();
        if (password.length() == 0) {
            forgotPasswordConfirmPassword.setError("Password can't be empty!!");
            return false;
        } else if (password.length() < 3) {
            forgotPasswordConfirmPassword.setError("Password too short!!");
            return false;
        } else if (password.length() > 12) {
            forgotPasswordConfirmPassword.setError("Password too long!!");
            return false;
        }
        forgotPasswordConfirmPassword.setError(null);
        return true;
    }

    private void passwordResetServerCall() {
        String mobile = forgotPasswordMobile.getEditText().getText().toString();
        String password = forgotPasswordNewPassword.getEditText().getText().toString();
        RetrofitApi.getPaniServicesObject().getForgotPasswordResponse(mobile, password).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    CommonResponse commonResponse = response.body();
                    if (commonResponse != null && commonResponse.getErrorCode().equals(TerminalConstant.SUCCESS)) {
                        Toast.makeText(ForgotPassword.this, commonResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPassword.this, Authentication.class));
                    } else if (commonResponse != null) {
                        Toast.makeText(ForgotPassword.this, commonResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_turn_internet_on), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ForgotPassword.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Facebook Otp verification
     * */

    private static final int APP_REQUEST_CODE = 99;

    private void sendFacebookOtpForVerification() {
        final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
            new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.CODE);
        // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult != null && loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult != null && loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult != null && loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format("Success:%s...", loginResult.getAuthorizationCode().substring(0, 10));
                }
            }
            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            passwordResetServerCall();
        }
    }
}
