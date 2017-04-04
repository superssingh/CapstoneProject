package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.Fragments.WidgetFragment;
import com.santossingh.capstoneproject.R;

public class WidgetActivity extends AppCompatActivity implements WidgetFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.TopBooks);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onFragmentInteraction(TopBooks book) {
        if (book != null) {
            Intent intent = new Intent(WidgetActivity.this, AmazonActivity.class)
                    .putExtra(String.valueOf(R.string.BUY_Amazon), book.getBuyLink());
            startActivity(intent);
        }
    }
}
