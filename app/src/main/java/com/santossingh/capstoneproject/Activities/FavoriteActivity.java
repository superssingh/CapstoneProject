package com.santossingh.capstoneproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private View view;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
//        getFavoriteList();
    }

//    private void getFavoriteList() {
//        results = realm.where(FavoriteMovies.class).findAll();
//        if (results.size()==0){
//            recyclerView.setVisibility(View.GONE);
//            emptyText.setText(R.string.Favorite_empty_list);
//            emptyText.setVisibility(View.VISIBLE);
//        }else{
//            configRecycleView(results);
//        }
//    }
//    private void configRecycleView(RealmResults<FavoriteMovies> results) {
//        AutofitGridlayout layoutManager = new AutofitGridlayout(FavoriteActivity.this, 320 );
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        recyclerAdapter = new FavoriteRecycleAdapter(this, results, FavoriteActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(recyclerAdapter);
//    }
//
//    @Override
//    public void onCurrentMovie(FavoriteMovies currentMovie) {
//        Intent intent = new Intent(this, DetailActivity.class)
//                .putExtra("movie_Id", currentMovie.getMovie_id())
//                .putExtra("movie_Name", currentMovie.getTitle())
//                .putExtra("poster_Path", currentMovie.getPoster_path())
//                .putExtra("back_poster_Path", currentMovie.getBackdrop_path())
//                .putExtra("release_Date", currentMovie.getRelease_date())
//                .putExtra("users_Rating", currentMovie.getVote_average())
//                .putExtra("overview", currentMovie.getOverview());
//        startActivity(intent);
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        getFavoriteList();
    }

}
