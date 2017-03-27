package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.santossingh.capstoneproject.Fragments.DetailFragment;
import com.santossingh.capstoneproject.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        setTitle(R.string.DetailActivity);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Before send data from activity to fragment, first  we need to extend a fragment class with android.app.Fragment.
        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.fragment_detail);
        detailFragment.setDataHandsetUI(intent);

    }

}
