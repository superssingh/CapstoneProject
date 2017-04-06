package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.santossingh.capstoneproject.R;

public class MainActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private String pendingService = "Business";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}