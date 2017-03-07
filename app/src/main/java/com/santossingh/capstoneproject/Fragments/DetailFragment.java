package com.santossingh.capstoneproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.santossingh.capstoneproject.Activities.AmazonActivity;
import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class DetailFragment extends Fragment {

    private static final String BOOK = "current_book";

    @BindView(R.id.ImageBar)
    ImageView imageView;
    @BindView(R.id.Amazon)
    Button Amazon;
    @BindView(R.id.BTNReview)
    Button REVIEW;
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
    String Buy_Link, Review_Link;

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
        setHandsetUI();
        setAllListener();
        return view;
    }

    public void setHandsetUI() {
        Title.setText(getArguments().getString(String.valueOf(R.string.BOOK_TITLE)));
        Author.setText(getArguments().getString(String.valueOf(R.string.AUTHOR)));
        Year.setText(getArguments().getString(String.valueOf(R.string.PUBLISHED_YEAR)));
        Price.setText(getArguments().getString(String.valueOf(R.string.PRICE)));
        String filteredDescription = filterTags(getArguments().getString(String.valueOf(R.string.DESCRIPTION)));
        Description.setText(filteredDescription);
        Picasso.with(getActivity()).load(getArguments().getString(String.valueOf(R.string.IMAGE)))
                .error(R.drawable.ic_error_black_24dp)
                .placeholder(R.drawable.book1).resize(300, 400)
                .into(imageView);
    }

    private String filterTags(String s) {
        String filter = "";
        filter = s.replaceAll("<p>", "");
        s = filter.replaceAll("</p>", "\n");
        filter = s.replaceAll("<b>", "");
        s = filter.replaceAll("</b>", "");
        filter = s.replaceAll("<br>", "\n");
        return filter;
    }

    public void setDataforTabletUI(final AmazonBook book) {
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());
        Year.setText(book.getPublishedDate());
        Price.setText(book.getPrice());
        Description.setText(book.getDescription());
        Review_Link = book.getReviews();
        Buy_Link = book.getDetailURL();
    }


    public void setDataforHandsetUI(final Intent intent) {
        Title.setText(intent.getStringExtra(String.valueOf(R.string.BOOK_TITLE)));
        Author.setText(intent.getStringExtra(String.valueOf(R.string.AUTHOR)));
        Year.setText(intent.getStringExtra(String.valueOf(R.string.PUBLISHED_YEAR)));
        Price.setText(intent.getStringExtra(String.valueOf(R.string.PRICE)));
        Description.setText(intent.getStringExtra(String.valueOf(R.string.DESCRIPTION)));
        Review_Link = intent.getStringExtra(String.valueOf(R.string.Review_Link));
        Buy_Link = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));

        Picasso.with(getActivity()).load(intent.getStringExtra(String.valueOf(R.string.IMAGE)))
                .error(R.drawable.ic_error_black_24dp).resize(300, 400).into(imageView);
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
    }

}
