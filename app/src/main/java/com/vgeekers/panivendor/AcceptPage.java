package com.vgeekers.panivendor;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AcceptPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_page);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Orders.class));
        finish();
    }
}
