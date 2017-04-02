package com.santossingh.capstoneproject.Models.Database.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by santoshsingh on 30/03/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FAVORITE.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_QUERY = "CREATE TABLE " +
            UserContract.TABLE_NAME + " (" +
            UserContract.ID + " TEXT," +
            UserContract.TITLE + " TEXT," +
            UserContract.AUTHOR + " TEXT," +
            UserContract.IMAGE + " TEXT," +
            UserContract.PRICE + " TEXT," +
            UserContract.PUB_DATE + " TEXT," +
            UserContract.BUY_LINK + " TEXT," +
            UserContract.REVIEW_LINK + " TEXT," +
            UserContract.DESCRIPTION + " TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addBook(String id, String title, String author, String image, String price, String pub_date, String buy_link, String review_link, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + UserContract.TABLE_NAME + " WHERE " + UserContract.ID + " = " + id + "";
        Cursor cur = db.rawQuery(sql, new String[0]);

        if (cur.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserContract.ID, id);
            contentValues.put(UserContract.TITLE, title);
            contentValues.put(UserContract.AUTHOR, author);
            contentValues.put(UserContract.IMAGE, image);
            contentValues.put(UserContract.PRICE, price);
            contentValues.put(UserContract.PUB_DATE, pub_date);
            contentValues.put(UserContract.BUY_LINK, buy_link);
            contentValues.put(UserContract.REVIEW_LINK, review_link);
            contentValues.put(UserContract.DESCRIPTION, description);

            db.insert(UserContract.TABLE_NAME, null, contentValues);
            Log.e("Saved", "Saved");
            cur.close();
            db.close();
            return true;
        } else {
            Log.e("Already", "Already");
            cur.close();
            db.close();
            return false;
        }
    }

    public ArrayList<String> getAllBooks() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + UserContract.TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(UserContract.TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public Integer deleteBook(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserContract.TABLE_NAME, UserContract.ID + " = ? ", new String[]{id});
    }

}
