package com.santossingh.capstoneproject.flavors.paid;

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
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.activities.DetailActivity;
import com.santossingh.capstoneproject.amazon.models.AmazonBook;
import com.santossingh.capstoneproject.database.realm_database.FavoriteBooks;
import com.santossingh.capstoneproject.fragments.AmazonFragment;
import com.santossingh.capstoneproject.fragments.DetailFragment;
import com.santossingh.capstoneproject.fragments.FavoriteFragment;
import com.santossingh.capstoneproject.fragments.GoogleFragment;
import com.santossingh.capstoneproject.google.models.Item;

import java.io.IOException;

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
    @BindView(R.id.RetryButton)
    ImageButton retryButton;
    @BindView(R.id.NoNetwork)
    LinearLayout noNetwork;
    @BindView(R.id.progressBarLayout)
    LinearLayout layoutProgressbar;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Realm initialization
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getString(R.string.RealmDatabaseName))
                .schemaVersion(Integer.parseInt(getString(R.string.VERSION)))
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        // * Initialize and add navigation drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startApp();
            }
        });

        startApp();
    }

    private void startApp() {
        if (isOnline()) {
            noNetwork.setVisibility(View.GONE);
            layoutProgressbar.setVisibility(View.GONE);
            AMAZON();
        } else {
            noNetwork.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.InternetNotAvailable, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec(getString(R.string.SYSTEM_PING));
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void runShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(getString(R.string.MessageType));
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.ExtraSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.ExtraText);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.Share)));
    }


    @Override
    public void onFragmentInteraction(AmazonBook book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
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
            detailFragment.setInfoInTabletUI(book);
        }
    }

    @Override
    public void onTabletIntraction(AmazonBook book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        Boolean has = (detailFragment != null);
        if (has) {
            detailFragment.setInfoInTabletUI(book);
        }
    }

    @Override
    public void onFragmentInteraction(Item book) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), book.getId())
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), book.getVolumeInfo().getTitle() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), book.getVolumeInfo().getAuthors() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getAuthors().get(0))
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), book.getVolumeInfo().getPublishedDate() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), book.getVolumeInfo().getImageLinks().getThumbnail())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), book.getVolumeInfo().getDescription() == null ? getString(R.string.FREE_DESCRIPTION_TAG) : book.getVolumeInfo().getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), getString(R.string.FREE_TAG))
                    .putExtra(String.valueOf(R.string.Review_Link), getString(R.string.Not_Available))
                    .putExtra(String.valueOf(R.string.BUY_Amazon), getString(R.string.Not_Available));
            startActivity(intent);
        } else {
            detailFragment.setFreeInfoInTabletUI(book);
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
            detailFragment.setFavoriteInfoInTablet(book);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void AMAZON() {
        setTitle(R.string.PaidBooks);
        AmazonFragment fragment = new AmazonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void GOOGLE() {
        setTitle(R.string.FreeBooks);
        GoogleFragment fragment = new GoogleFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void FAVORITE() {
        setTitle(R.string.FavoriteBooks);
        FavoriteFragment fragment = new FavoriteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

}