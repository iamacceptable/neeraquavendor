package com.vgeekers.panivendor;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RejectPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_page);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Orders.class));
        finish();
    }
}
