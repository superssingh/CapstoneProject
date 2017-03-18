package com.santossingh.capstoneproject.ContentProvider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.Models.Database.FavoriteBooks;
import com.santossingh.capstoneproject.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by santoshsingh on 18/03/17.
 */

public class MyContentProvider {

    private Context context;

    public MyContentProvider() {
    }
    // Add method with Realm Asynchronous Transactions---------------------------
    // To write data on background thread which avoid blocking the UI thread----
    // Adding current movie info which comes from Intent (Handset view)----------


    public void addBookFromIntent(final Context context, final Intent intent) {
        this.context = context;
        final String book_id = intent.getStringExtra(String.valueOf(R.string.BOOK_ID));
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteBooks> Fav_Book = realm.where(FavoriteBooks.class)
                .equalTo("id", book_id)
                .findAllAsync();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                FavoriteBooks favoriteBook = bgRealm.createObject(FavoriteBooks.class, book_id);
//                favoriteBook.setId(intent.getStringExtra(String.valueOf(R.string.BOOK_ID)));
                favoriteBook.setTitle(intent.getStringExtra(String.valueOf(R.string.BOOK_TITLE)));
                favoriteBook.setAuthor(intent.getStringExtra(String.valueOf(R.string.AUTHOR)));
                favoriteBook.setImage(intent.getStringExtra(String.valueOf(R.string.IMAGE)));
                favoriteBook.setPrice(intent.getStringExtra(String.valueOf(R.string.PRICE)));
                favoriteBook.setPublishedDate(intent.getStringExtra(String.valueOf(R.string.PUBLISHED_YEAR)));
                favoriteBook.setDescription(intent.getStringExtra(String.valueOf(R.string.DESCRIPTION)));
                favoriteBook.setReviewLink(intent.getStringExtra(String.valueOf(R.string.Review_Link)));
                favoriteBook.setBuyLink(intent.getStringExtra(String.valueOf(R.string.BUY_Amazon)));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, R.string.FavoriteMarked, Toast.LENGTH_SHORT)
                        .show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // delete method (for un-favorite) when it already exists--
                Log.e("Error:", error.getMessage());
                Toast.makeText(context, R.string.Already_exists, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    // adding selected movie info which comes from ArratList (Tablet view)
    public void addBookFromTabletUI(final Context context, final AmazonBook book) {
        this.context = context;
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteBooks> Fav_Book = realm.where(FavoriteBooks.class)
                .equalTo("id", book.getAsin())
                .findAllAsync();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                FavoriteBooks favoriteBook = bgRealm.createObject(FavoriteBooks.class);
                favoriteBook.setId(book.getAsin());
                favoriteBook.setTitle(book.getTitle());
                favoriteBook.setAuthor(book.getAuthor());
                favoriteBook.setImage(book.getImage());
                favoriteBook.setPrice(book.getPrice());
                favoriteBook.setPublishedDate(book.getPublishedDate());
                favoriteBook.setDescription(book.getDescription());
                favoriteBook.setReviewLink(book.getReviews());
                favoriteBook.setBuyLink(book.getDetailURL());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, R.string.FavoriteMarked, Toast.LENGTH_SHORT)
                        .show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //delete method (for un-favorite) when it already exists--------------------------
                Toast.makeText(context, R.string.Already_exists, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
