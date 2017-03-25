package com.santossingh.capstoneproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.santossingh.capstoneproject.ContentProvider.MyContentProvider;
import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.Models.Amazon.Constants;
import com.santossingh.capstoneproject.Models.Google.Item;
import com.santossingh.capstoneproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class DetailFragment extends android.app.Fragment {

    private final static String FREE_BOOK = "FREE";
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
    View view;
    String Buy_Link = "", Review_Link = "", Book_ID = "";
    AmazonBook book;
    Item FreeBook;

    public DetailFragment() {
        setHasOptionsMenu(true);
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
                        .putExtra(String.valueOf(R.string.URL_Link), Buy_Link);
                startActivity(intent);
            }
        });

        REVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmazonActivity.class)
                        .putExtra(String.valueOf(R.string.URL_Link), Review_Link);
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
        Amazon.setVisibility(View.VISIBLE);
        REVIEW.setVisibility(View.VISIBLE);
        Google_Preview.setVisibility(View.GONE);
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());
        Year.setText(book.getPublishedDate());
        Price.setText(book.getPrice());
        String filteredDescription = filterTags(book.getDescription() + " ");
        Description.setText(filteredDescription);
        Review_Link = book.getReviews();
        Buy_Link = book.getDetailURL();
        Picasso.with(getActivity()).load(book.getImage())
                .placeholder(R.mipmap.ic_book).resize(300, 400)
                .into(imageView);

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyContentProvider contentProvider = new MyContentProvider();
                contentProvider.addBookFromTabletUIForPaid(getActivity(), book1);
            }
        });
    }

    public void setFreeDataforTabletUI(final Item book) {
        FreeBook = book;
        Book_ID = book.getId();
        Title.setText(book.getVolumeInfo().getTitle() == null ? "N/A" : book.getVolumeInfo().getTitle());
        Author.setText(book.getVolumeInfo().getAuthors() == null ? "N/A" : book.getVolumeInfo().getAuthors().get(0));
        Year.setText(book.getVolumeInfo().getPublishedDate() == null ? "N/A" : book.getVolumeInfo().getPublishedDate());
        Price.setText(Constants.FREE_TAG);
        Description.setText(Constants.FREE_DESCRIPTION_TAG);
        Picasso.with(getActivity()).load(book.getVolumeInfo().getImageLinks().getThumbnail())
                .placeholder(R.mipmap.ic_book).resize(300, 400)
                .into(imageView);
        Google_Preview.setVisibility(View.VISIBLE);
        Amazon.setVisibility(View.GONE);
        REVIEW.setVisibility(View.GONE);

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Hi", Toast.LENGTH_LONG).show();
                MyContentProvider contentProvider = new MyContentProvider();
                contentProvider.addBookFromTabletUIForFree(getActivity(), book);
            }
        });
    }


    public void setDataHandsetUI(final Intent intent) {
        String priceStatus = intent.getStringExtra(String.valueOf(R.string.PRICE));

        if (!priceStatus.equals(FREE_BOOK)) {
            Google_Preview.setVisibility(View.GONE);
            REVIEW.setVisibility(View.VISIBLE);
            Amazon.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), Book_ID, Toast.LENGTH_LONG).show();
            REVIEW.setVisibility(View.GONE);
            Amazon.setVisibility(View.GONE);
            Google_Preview.setVisibility(View.VISIBLE);
        }

        Title.setText(intent.getStringExtra(String.valueOf(R.string.BOOK_TITLE)));
        Author.setText(intent.getStringExtra(String.valueOf(R.string.AUTHOR)));
        Year.setText(intent.getStringExtra(String.valueOf(R.string.PUBLISHED_YEAR)));
        Price.setText(intent.getStringExtra(String.valueOf(R.string.PRICE)));

        Description.setText(filterTags(intent.getStringExtra(String.valueOf(R.string.DESCRIPTION))));
        Picasso.with(getActivity()).load(intent.getStringExtra(String.valueOf(R.string.IMAGE)))
                .placeholder(R.mipmap.ic_book).resize(300, 400)
                .into(imageView);
        Buy_Link = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));
        Review_Link = intent.getStringExtra(String.valueOf(R.string.Review_Link));

        FAVORITE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyContentProvider contentProvider = new MyContentProvider();
                contentProvider.addBookFromIntent(getActivity(), intent);
            }
        });
    }

    private String filterTags(String s) {
        String filter = android.text.Html.fromHtml(s).toString();
        return filter;
    }


}
