package com.santossingh.capstoneproject.Models.Database.SQLiteDB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by santoshsingh on 31/03/17.
 */

public class MyDataProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.santossingh.capstoneproject.DataProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/" + UserContract.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final int BOOKS = 1;
    static final int BOOK_ID = 2;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> DATA_PROJECTION_MAP;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, UserContract.TABLE_NAME, BOOKS);
        uriMatcher.addURI(PROVIDER_NAME, UserContract.TABLE_NAME + "/#", BOOK_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserContract.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case BOOKS:
                qb.setProjectionMap(DATA_PROJECTION_MAP);
                break;

            case BOOK_ID:
                qb.appendWhere(UserContract.ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == "") {
            /**
             * By default sort on book title
             */
            sortOrder = UserContract.TITLE;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        /**
         * Add a new book
         */
        long rowID = db.insert(UserContract.TABLE_NAME, "", contentValues);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                count = db.delete(UserContract.TABLE_NAME, selection, selectionArgs);
                break;

            case BOOK_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(UserContract.TABLE_NAME, UserContract.ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                count = db.update(UserContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case BOOK_ID:
                count = db.update(UserContract.TABLE_NAME, contentValues,
                        UserContract.ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
