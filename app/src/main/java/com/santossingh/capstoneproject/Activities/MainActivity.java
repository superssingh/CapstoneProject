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

public class MainActivity extends AppCompatActivity implements AmazonFragment.OnFragmentInteractionListener, GoogleFragment.OnFragmentInteractionListener {

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

        AmazonFragment fragment = new AmazonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_amazon) {
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(AmazonBook mData) {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);

        if (detailFragment != null) {
            detailFragment.setDataforTabletUI(mData);
        } else {

            Toast.makeText(this, mData.getPrice(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(String.valueOf(R.string.BOOK_TITLE), mData.getTitle())
                    .putExtra(String.valueOf(R.string.AUTHOR), mData.getAuthor())
                    .putExtra(String.valueOf(R.string.PUBLISHED_YEAR), mData.getPublishedDate())
                    .putExtra(String.valueOf(R.string.IMAGE), mData.getImage())
                    .putExtra(String.valueOf(R.string.DESCRIPTION), mData.getDescription())
                    .putExtra(String.valueOf(R.string.PRICE), mData.getPrice())
                    .putExtra(String.valueOf(R.string.Review_Link), mData.getReviews())
                    .putExtra(String.valueOf(R.string.BUY_Amazon), mData.getDetailURL());
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Item mData) {
//        Toast.makeText(this,mData.getVolumeInfo().getTitle(),Toast.LENGTH_LONG).show();
    }
}
