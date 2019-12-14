package com.vgeekers.panivendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.vgeekers.panivendor.adapters.AuthPageAdapter;

public class Authentication extends AppCompatActivity {

    TabLayout authTab;
    ViewPager authViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        authTab = findViewById(R.id.authTab);
        authViewPager = findViewById(R.id.authViewPager);
        AuthPageAdapter authPageAdapter = new AuthPageAdapter(getSupportFragmentManager());
        authViewPager.setAdapter(authPageAdapter);
        authTab.setupWithViewPager(authViewPager);
    }
}
