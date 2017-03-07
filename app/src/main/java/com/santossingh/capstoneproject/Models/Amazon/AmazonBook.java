package com.santossingh.capstoneproject.Models.Amazon;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;

import com.santossingh.capstoneproject.R;

/**
 * Created by santoshsingh on 22/02/17.
 */

public class AmazonBook extends BaseObservable {
    private String detailURL;
    private String image;
    private String author;
    private String price;
    private String publishedDate;
    private String title;
    private String reviews;
    private String description;

    public AmazonBook() {
    }

    public AmazonBook(String detailURL, String image, String author, String price, String publishedDate, String title, String reviews, String description) {
        this.detailURL = detailURL;
        this.image = image;
        this.author = author;
        this.price = price;
        this.publishedDate = publishedDate;
        this.title = title;
        this.reviews = reviews;
        this.description = description;
    }

    public AmazonBook(Bundle bundle) {
        title = bundle.getString(String.valueOf(R.string.BOOK_TITLE));
        author = bundle.getString(String.valueOf(R.string.AUTHOR));
        publishedDate = bundle.getString(String.valueOf(R.string.PUBLISHED_YEAR));
        image = bundle.getString(String.valueOf(R.string.IMAGE));
        price = bundle.getString(String.valueOf(R.string.PRICE));
        description = bundle.getString(String.valueOf(R.string.DESCRIPTION));
        reviews = bundle.getString(String.valueOf(R.string.Review_Link));
        detailURL = bundle.getString(String.valueOf(R.string.BUY_LINK));
    }

    @Bindable
    public String getDetailURL() {
        return detailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Bindable
    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
