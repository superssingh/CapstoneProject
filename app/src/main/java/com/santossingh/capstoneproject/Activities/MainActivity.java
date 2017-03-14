package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.santossingh.capstoneproject.Fragments.AmazonFragment;
import com.santossingh.capstoneproject.Fragments.DetailFragment;
import com.santossingh.capstoneproject.Fragments.FavoriteFragment;
import com.santossingh.capstoneproject.Fragments.GoogleFragment;
import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.Models.Google.Item;
import com.santossingh.capstoneproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AmazonFragment.OnFragmentInteractionListener, GoogleFragment.OnFragmentInteractionListener, FavoriteFragment.OnFragmentInteractionListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_toolbar)
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

        refreshAction();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_amazon) {
            onRestart();
            setTitle("Amazon Library");

            AmazonFragment fragment = new AmazonFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

        } else if (id == R.id.nav_google) {
            setTitle("Google Library");
            GoogleFragment fragment = new GoogleFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

        } else if (id == R.id.nav_favorite) {
            setTitle("Favorites Books");
            FavoriteFragment fragment = new FavoriteFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.my_search_bar:
                searchAction();
                return true;

            case R.id.refresh:
                refreshAction();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshAction() {
        setTitle("Top Paid Books");
        AmazonFragment fragment = new AmazonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void searchAction() {
        Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(AmazonBook book) {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Toast.makeText(this, book.getPrice(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), R.string.NULL)
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
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment != null) {
            detailFragment.setDataforTabletUI(book);
        }
    }

    @Override
    public void onFragmentInteraction(Item book) {

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        if (detailFragment == null) {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_ID), book.getId())
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), book.getVolumeInfo().getTitle() == null ? "[N/A]" : book.getVolumeInfo().getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), book.getVolumeInfo().getAuthors() == null ? "[N/A]" : book.getVolumeInfo().getAuthors().get(0))
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), book.getVolumeInfo().getPublishedDate() == null ? "[N/A]" : book.getVolumeInfo().getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), book.getVolumeInfo().getImageLinks().getThumbnail())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), book.getVolumeInfo().getDescription() == null ? "[N/A]" : book.getVolumeInfo().getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), "[FREE]")
                    .putExtra(String.valueOf(R.string.Review_Link), "N/A")
                    .putExtra(String.valueOf(R.string.BUY_Amazon), "N/A");
            startActivity(intent);
        } else {
            detailFragment.setFreeDataforTabletUI(book);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}