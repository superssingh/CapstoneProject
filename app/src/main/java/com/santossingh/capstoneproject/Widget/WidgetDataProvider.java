package com.santossingh.capstoneproject.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<String> collection = new ArrayList<>();
    Context context;
    Intent intent;
    int appWidgetIds;
    List<TopBooks> topBooksList;
    DatabaseReference databaseReference;

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        appWidgetIds = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        topBooksList = new ArrayList<>();
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
        return topBooksList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        URL url = null;
        Bitmap image = null;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);
        remoteViews.setTextViewText(R.id.widgetTitle, topBooksList.get(position).getTitle());

        try {
            url = new URL(topBooksList.get(position).getImage());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.widgetImage, image);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
