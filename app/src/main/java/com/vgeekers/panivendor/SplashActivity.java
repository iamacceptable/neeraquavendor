package com.vgeekers.panivendor;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static com.vgeekers.panivendor.TerminalConstant.MY_PREFS;
import static com.vgeekers.panivendor.TerminalConstant.USER_LOGIN_DONE_KEY;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
                boolean isLoginDone = prefs.getBoolean(USER_LOGIN_DONE_KEY, false);
                if (isLoginDone) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, Authentication.class));
                }
                finish();
            }
        }, 2500);
    }
}
