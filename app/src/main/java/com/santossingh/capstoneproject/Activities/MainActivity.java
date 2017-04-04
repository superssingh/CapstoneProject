package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Database.RealmLocalDB.FavoriteBooks;
import com.santossingh.capstoneproject.Fragments.AmazonFragment;
import com.santossingh.capstoneproject.Fragments.DetailFragment;
import com.santossingh.capstoneproject.Fragments.FavoriteFragment;
import com.santossingh.capstoneproject.Fragments.GoogleFragment;
import com.santossingh.capstoneproject.Google.Models.Item;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements AmazonFragment.OnFragmentInteractionListener, GoogleFragment.OnFragmentInteractionListener, FavoriteFragment.OnFragmentInteractionListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.customToolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //Realm initialization
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("favorite.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        // * Initialize and add navigation drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        AMAZON();
    }

    @Override
    public void onFragmentInteraction(AmazonBook book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Toast.makeText(this, book.getAsin(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), book.getAsin())
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), book.getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), book.getAuthor())
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), book.getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), book.getImage())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), book.getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), book.getPrice())
                    .putExtra(String.valueOf(R.string.Review_Link), book.getReviews())
                    .putExtra(String.valueOf(R.string.BUY_Amazon), book.getDetailURL());
            startActivity(intent);
        } else {
            detailFragment.setDataforTabletUI(book);
        }
    }

    @Override
    public void onTabletIntraction(AmazonBook book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment != null) {
            detailFragment.setDataforTabletUI(book);
        }
    }

    @Override
    public void onFragmentInteraction(Item book) {

        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), book.getId())
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), book.getVolumeInfo().getTitle() == null ? "[N/A]" : book.getVolumeInfo().getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), book.getVolumeInfo().getAuthors() == null ? "[N/A]" : book.getVolumeInfo().getAuthors().get(0))
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), book.getVolumeInfo().getPublishedDate() == null ? "[N/A]" : book.getVolumeInfo().getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), book.getVolumeInfo().getImageLinks().getThumbnail())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), book.getVolumeInfo().getDescription() == null ? Constants.FREE_DESCRIPTION_TAG : book.getVolumeInfo().getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), Constants.FREE_TAG)
                    .putExtra(String.valueOf(R.string.Review_Link), "N/A")
                    .putExtra(String.valueOf(R.string.BUY_Amazon), "N/A");
            startActivity(intent);
        } else {
            detailFragment.setFreeDataforTabletUI(book);
        }
    }

    @Override
    public void onFragmentInteraction(FavoriteBooks book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), book.getId())
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), book.getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), book.getAuthor())
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), book.getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), book.getImage())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), book.getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), book.getPrice())
                    .putExtra(String.valueOf(R.string.Review_Link), book.getReviewLink())
                    .putExtra(String.valueOf(R.string.BUY_Amazon), book.getBuyLink());
            startActivity(intent);
        } else {
            detailFragment.setFavoriteDataforTabletUI(book);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Locate MenuItem with ShareActionProvider
        return true;
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_amazon) {
            AMAZON();
        } else if (id == R.id.nav_google) {
            GOOGLE();
        } else if (id == R.id.nav_favorite) {
            FAVORITE();
        } else if (id == R.id.menu_item_share) {
            runShare();
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }

    // Call to update the share intent

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void AMAZON() {
        setTitle("Paid Books");
        AmazonFragment fragment = new AmazonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void GOOGLE() {
        setTitle("Free Books");
        GoogleFragment fragment = new GoogleFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void FAVORITE() {
        setTitle("Favorite List");
        FavoriteFragment fragment = new FavoriteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void runShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Awesome Reader App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I believe that This is an amazing app for readers. Just try this here is the link  http://myappwebsitelink.com");
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

}