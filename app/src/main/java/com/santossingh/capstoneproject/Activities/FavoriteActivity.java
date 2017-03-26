package com.santossingh.capstoneproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.santossingh.capstoneproject.Adatpers.FavoriteRecyclerAdapter;
import com.santossingh.capstoneproject.Models.Database.FavoriteBooks;
import com.santossingh.capstoneproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.Favorite_recycleView)
    RecyclerView recyclerView;
    private RealmResults<FavoriteBooks> booksList;
    private FavoriteRecyclerAdapter recyclerViewAdapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
