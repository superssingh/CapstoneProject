package com.santossingh.capstoneproject.Database.RealmLocalDB;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Google.Models.Item;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.Constants;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by santoshsingh on 18/03/17.
 */

public class RealmContentProvider {

    private Context context;

    public RealmContentProvider() {
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
        realm.close();
    }

    // adding selected movie info which comes from ArratList (Tablet view)
    public void addBookFromTabletUIForPaid(final Context context, final AmazonBook book) {
        this.context = context;
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteBooks> Fav_Book = realm.where(FavoriteBooks.class)
                .equalTo("id", book.getAsin())
                .findAllAsync();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                FavoriteBooks favoriteBook = bgRealm.createObject(FavoriteBooks.class, book.getAsin());
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
        realm.close();
    }

    public void addBookFromTabletUIForFree(final Context context, final Item book) {
        this.context = context;
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteBooks> Fav_Book = realm.where(FavoriteBooks.class)
                .equalTo("id", book.getId())
                .findAllAsync();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                FavoriteBooks favoriteBook = bgRealm.createObject(FavoriteBooks.class, book.getId());
                favoriteBook.setTitle(book.getVolumeInfo().getTitle());
                favoriteBook.setAuthor(book.getVolumeInfo().getAuthors() == null ? Constants.NOT_AVAILABLE : book.getVolumeInfo().getAuthors().get(0));
                favoriteBook.setImage(Constants.NOT_AVAILABLE);
                favoriteBook.setPrice(Constants.FREE_TAG);
                favoriteBook.setPublishedDate(book.getVolumeInfo().getPublishedDate() == null ? Constants.NOT_AVAILABLE : book.getVolumeInfo().getPublishedDate());
                favoriteBook.setDescription(book.getVolumeInfo().getDescription() == null ? Constants.FREE_DESCRIPTION_TAG : book.getVolumeInfo().getDescription());
                favoriteBook.setReviewLink(Constants.NOT_AVAILABLE);
                favoriteBook.setBuyLink(Constants.NOT_AVAILABLE);
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
                Toast.makeText(context, R.string.Already_exists, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        realm.close();
    }

    public RealmResults<FavoriteBooks> getBooksList() {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteBooks> Fav_Book = realm.where(FavoriteBooks.class).findAllAsync();
        realm.close();
        return Fav_Book;
    }
}
