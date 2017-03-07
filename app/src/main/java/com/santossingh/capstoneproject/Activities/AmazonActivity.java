package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.Fragments.AmazonFragment;
import com.santossingh.capstoneproject.R;

public class AmazonActivity extends AppCompatActivity implements AmazonFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        Intent intent = getIntent();
        String Buy_link = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));
        Toast.makeText(this, Buy_link, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFragmentInteraction(AmazonBook mData) {

    }
}
