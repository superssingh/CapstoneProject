package com.santossingh.capstoneproject.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.santossingh.capstoneproject.Activities.AmazonActivity;
import com.santossingh.capstoneproject.Activities.ViewActivity;
import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Database.RealmLocalDB.FavoriteBooks;
import com.santossingh.capstoneproject.Database.RealmLocalDB.RealmContentProvider;
import com.santossingh.capstoneproject.Google.Models.Item;
import com.santossingh.capstoneproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends android.app.Fragment {

    @BindView(R.id.ImageBar)
    ImageView imageView;
    @BindView(R.id.Amazon)
    Button Amazon_Button;
    @BindView(R.id.BTNReview)
    Button REVIEW_Button;
    @BindView(R.id.G_Preview)
    ImageButton Google_Preview;
    @BindView(R.id.fab_fav)
    FloatingActionButton FAVORITE;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.Detail_Title)
    TextView Title;
    @BindView(R.id.Detail_Author)
    TextView Author;
    @BindView(R.id.Detail_YEAR)
    TextView Year;
    @BindView(R.id.Detail_Price)
    TextView Price;
    @BindView(R.id.Detail_Description)
    TextView Description;
    @BindView(R.id.CoordinateLayout)
    CoordinatorLayout coordinatorLayout;
    private String Buy_Link = "", Review_Link = "", Book_ID = "";
    private AmazonBook book;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        book = new AmazonBook();
        setInfoInTabletUI(book);

        setUIFont(view);
        setAllListener();

        return view;
    }

    private void setAllListener() {

        REVIEW_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmazonActivity.class)
                        .putExtra(String.valueOf(R.string.URL_Link), Review_Link);
                startActivity(intent);
            }
        });

        Amazon_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmazonActivity.class)
                        .putExtra(String.valueOf(R.string.URL_Link), Buy_Link);
                startActivity(intent);
            }
        });

        Google_Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewActivity.class)
                        .putExtra(String.valueOf(R.string.BOOK_ID), Book_ID);
                startActivity(intent);
            }
        });

    }

    public void setInfoInTabletUI(final AmazonBook book1) {
        book = book1;
        hasAmazon(true);
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());
        Year.setText(book.getPublishedDate());
        Price.setText(book.getPrice());
        Description.setText(filterTags(book.getDescription() + " "));
        Review_Link = book.getReviews();
        Buy_Link = book.getDetailURL();
        setImage(book.getImage());

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmContentProvider contentProvider = new RealmContentProvider();
                contentProvider.addBookFromTabletUIForPaid(getActivity(), book);
            }
        });

    }

    public void setFreeInfoInTabletUI(final Item book) {
        Book_ID = book.getId();
        Title.setText(book.getVolumeInfo().getTitle() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getTitle());
        Author.setText(book.getVolumeInfo().getAuthors() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getAuthors().get(0));
        Year.setText(book.getVolumeInfo().getPublishedDate() == null ? getString(R.string.Not_Available) : book.getVolumeInfo().getPublishedDate());
        Price.setText(getString(R.string.FREE_TAG));
        Description.setText(getString(R.string.FREE_DESCRIPTION_TAG));
        setImage(book.getVolumeInfo().getImageLinks().getThumbnail());

        hasAmazon(false);

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmContentProvider contentProvider = new RealmContentProvider();
                contentProvider.addBookFromTabletUIForFree(getActivity(), book);
            }
        });
    }

    public void setDataHandsetUI(final Intent intent) {
        Book_ID = intent.getStringExtra(String.valueOf(R.string.BOOK_ID));
        String priceStatus = intent.getStringExtra(String.valueOf(R.string.PRICE));

        if (priceStatus.equals(getString(R.string.FREE_TAG))) {
            hasAmazon(false);
        } else {
            hasAmazon(true);
        }

        Title.setText(intent.getStringExtra(String.valueOf(R.string.BOOK_TITLE)));
        Author.setText(intent.getStringExtra(String.valueOf(R.string.AUTHOR)));
        Year.setText(intent.getStringExtra(String.valueOf(R.string.PUBLISHED_YEAR)));
        Price.setText(intent.getStringExtra(String.valueOf(R.string.PRICE)));
        Description.setText(filterTags(intent.getStringExtra(String.valueOf(R.string.DESCRIPTION))));
        setImage(intent.getStringExtra(String.valueOf(R.string.IMAGE)));
        Buy_Link = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));
        Review_Link = intent.getStringExtra(String.valueOf(R.string.Review_Link));

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmContentProvider contentProvider = new RealmContentProvider();
                contentProvider.addBookFromIntent(getActivity(), intent);
            }
        });
    }

    private String filterTags(String string) {
        String filter;
        if (string != null) {
            //noinspection deprecation
            filter = Html.fromHtml(string).toString();
        } else {
            filter = String.valueOf(R.string.Not_Available);
        }
        return filter;
    }

    public void setFavoriteInfoInTablet(final FavoriteBooks book) {
        if (book.getPrice().equals(getString(R.string.FREE_TAG))) {
            hasAmazon(false);
        } else {
            hasAmazon(true);
        }

        setImage(book.getImage());
        Book_ID = book.getId();
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());
        Year.setText(book.getPublishedDate());
        Price.setText(book.getPrice());
        Description.setText(filterTags(book.getDescription()));
        Buy_Link = book.getBuyLink();
        Review_Link = book.getReviewLink();

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.Already_exists, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setImage(String imageURL) {
        Picasso.with(getActivity()).load(imageURL)
                .fit()
                .placeholder(R.mipmap.placeholder)
                .into(imageView);
    }

    private void hasAmazon(boolean b) {
        if (b) {
            Google_Preview.setVisibility(View.GONE);
            REVIEW_Button.setVisibility(View.VISIBLE);
            Amazon_Button.setVisibility(View.VISIBLE);
        } else {
            REVIEW_Button.setVisibility(View.GONE);
            Amazon_Button.setVisibility(View.GONE);
            Google_Preview.setVisibility(View.VISIBLE);
        }
    }

    private void setUIFont(View view) {
        final TextView descriptionView = (TextView) view.findViewById(R.id.Detail_Description);
        descriptionView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Regular.ttf"));
    }

}
