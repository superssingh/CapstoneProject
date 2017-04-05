package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.santossingh.capstoneproject.Activities.AmazonActivity;
import com.santossingh.capstoneproject.Activities.ViewActivity;
import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.Database.RealmLocalDB.FavoriteBooks;
import com.santossingh.capstoneproject.Database.RealmLocalDB.RealmContentProvider;
import com.santossingh.capstoneproject.Google.Models.Item;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class DetailFragment extends android.app.Fragment {

    private static Typeface mFont;
    @BindView(R.id.ImageBar)
    ImageView imageView;
    @BindView(R.id.Amazon)
    Button Amazon;
    @BindView(R.id.BTNReview)
    Button REVIEW;
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
    List<TopBooks> topBooksList;
    DatabaseReference databaseReference;
    private View view;
    private String Buy_Link = "", Review_Link = "", Book_ID = "";
    private AmazonBook book;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    public static Typeface getTypeface(Context context, String typeface) {

        if (mFont == null) {
            mFont = Typeface.createFromAsset(context.getAssets(), typeface);
        }
        return mFont;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        book = new AmazonBook();
        if (book != null) {
            setDataforTabletUI(book);
        }
        setAllListener();
        return view;
    }

    private void setAllListener() {
        Amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmazonActivity.class)
                        .putExtra(String.valueOf(R.string.BUY_Amazon), Buy_Link);
                startActivity(intent);
            }
        });
        REVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmazonActivity.class)
                        .putExtra(String.valueOf(R.string.Review_Link), Review_Link);
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

    public void setDataforTabletUI(final AmazonBook book1) {
        book = book1;
        FAVORITE.setVisibility(View.VISIBLE);
        Amazon.setVisibility(View.VISIBLE);
        REVIEW.setVisibility(View.VISIBLE);
        Google_Preview.setVisibility(View.GONE);
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
//                addFavorite(book);
            }
        });
    }

    public void setFreeDataforTabletUI(final Item book) {
        Book_ID = book.getId();
        Title.setText(book.getVolumeInfo().getTitle() == null ? "N/A" : book.getVolumeInfo().getTitle());
        Author.setText(book.getVolumeInfo().getAuthors() == null ? "N/A" : book.getVolumeInfo().getAuthors().get(0));
        Year.setText(book.getVolumeInfo().getPublishedDate() == null ? "N/A" : book.getVolumeInfo().getPublishedDate());
        Price.setText(Constants.FREE_TAG);
        Description.setText(Constants.FREE_DESCRIPTION_TAG);
        setImage(book.getVolumeInfo().getImageLinks().getThumbnail());

        Google_Preview.setVisibility(View.VISIBLE);
        Amazon.setVisibility(View.GONE);
        REVIEW.setVisibility(View.GONE);

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmContentProvider contentProvider = new RealmContentProvider();
                contentProvider.addBookFromTabletUIForFree(getActivity(), book);
//                addFavorite(book);
            }
        });
    }

    public void setDataHandsetUI(final Intent intent) {
        Book_ID = intent.getStringExtra(String.valueOf(R.string.BOOK_ID));
        String priceStatus = intent.getStringExtra(String.valueOf(R.string.PRICE));

        if (!priceStatus.equals(Constants.FREE_TAG)) {
            Google_Preview.setVisibility(View.GONE);
            REVIEW.setVisibility(View.VISIBLE);
            Amazon.setVisibility(View.VISIBLE);
        } else {
            Google_Preview.setVisibility(View.VISIBLE);
            REVIEW.setVisibility(View.GONE);
            Amazon.setVisibility(View.GONE);
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
            filter = Html.fromHtml(string).toString();
        } else {
            filter = "[" + Constants.NOT_AVAILABLE + "]";
        }
        return filter;
    }

    public void setFavoriteDataforTabletUI(final FavoriteBooks book) {
        if (!book.getPrice().equals(Constants.FREE_TAG)) {
            Google_Preview.setVisibility(View.GONE);
            REVIEW.setVisibility(View.VISIBLE);
            Amazon.setVisibility(View.VISIBLE);
        } else {
            REVIEW.setVisibility(View.GONE);
            Amazon.setVisibility(View.GONE);
            Google_Preview.setVisibility(View.VISIBLE);
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
        Picasso.with(getActivity())
                .load(imageURL)
                .resize(300, 480)
                .placeholder(R.mipmap.placeholder)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        if (bitmap != null) {
//                            Palette.from(bitmap)
//                                    .generate(new Palette.PaletteAsyncListener() {
//                                        @Override
//                                        public void onGenerated(Palette palette) {
//                                            Palette.Swatch vibrantSwatch = palette.getDominantSwatch();
//                                            if (vibrantSwatch != null) {
//                                                collapsingToolbarLayout.setBackgroundColor(vibrantSwatch.getRgb());
//                                            }
//                                        }
//                                    });
                        }
                    }

                    @Override
                    public void onError() {
                        Snackbar snackbar = Snackbar
                                .make(collapsingToolbarLayout, "Error on image load.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
    }

}
