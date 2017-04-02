package com.santossingh.capstoneproject.OldWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.capstoneproject.Models.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 */
public class ListProvider implements RemoteViewsFactory {
    List<TopBooks> topBooksList;
    DatabaseReference databaseReference;
    private Context context = null;
    private int appWidgetId;
    private int mCount;

    public ListProvider(final Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        topBooksList = new ArrayList<TopBooks>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("topbooks");
        getDataFromFirebase();
    }

    @Override
    public void onDataSetChanged() {
        synchronized (topBooksList) {
            topBooksList.notify();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        return topBooksList == null ? 0 : topBooksList.size();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        final RemoteViews remoteViews;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        TopBooks books = topBooksList.get(position);
        if (topBooksList != null) {
//            remoteViews.setTextViewText(R.id.widgettext1, books.getTitle());
//            URL url = null; 
//            Bitmap image = null;
//            try { 
//                url = new URL(books.getImage());
//                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } 
//            remoteViews.setImageViewBitmap(R.id.widgetImage, image); 
        }
        return remoteViews;
    }

    public void getDataFromFirebase() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                topBooksList.add(0, topBooks);
                onDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                topBooksList.clear();
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                topBooksList.add(0, topBooks);
                onDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (topBooksList != null) {
                    topBooksList.clear();
                }
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                topBooksList.add(0, topBooks);
                onDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}