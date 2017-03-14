package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.santossingh.capstoneproject.Fragments.DetailFragment;
import com.santossingh.capstoneproject.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        sendData(intent);
    }

    private void sendData(Intent intent) {
        //PACK DATA IN A BUNDLE
        Bundle bundle = new Bundle();

        bundle.putString(String.valueOf(R.string.BOOK_ID), intent.getStringExtra(String.valueOf(R.string.BOOK_ID)));
        bundle.putString(String.valueOf(R.string.BOOK_TITLE), intent.getStringExtra(String.valueOf(R.string.BOOK_TITLE)));
        bundle.putString(String.valueOf(R.string.AUTHOR), intent.getStringExtra(String.valueOf(R.string.AUTHOR)));
        bundle.putString(String.valueOf(R.string.PUBLISHED_YEAR), intent.getStringExtra(String.valueOf(R.string.PUBLISHED_YEAR)));
        bundle.putString(String.valueOf(R.string.IMAGE), intent.getStringExtra(String.valueOf(R.string.IMAGE)));
        bundle.putString(String.valueOf(R.string.DESCRIPTION), intent.getStringExtra(String.valueOf(R.string.DESCRIPTION)));
        bundle.putString(String.valueOf(R.string.PRICE), intent.getStringExtra(String.valueOf(R.string.PRICE)));
        bundle.putString(String.valueOf(R.string.Review_Link), intent.getStringExtra(String.valueOf(R.string.Review_Link)));
        bundle.putString(String.valueOf(R.string.BUY_Amazon), intent.getStringExtra(String.valueOf(R.string.BUY_Amazon)));

        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).commit();
    }
}
