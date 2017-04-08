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

    private static final String FIREBASE_DATABASE_PATH = "topbooks";
    private Context context;
    private Intent intent;
    private int appWidgetIds;
    private List<TopBooks> topBooksList = new ArrayList<>();

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        appWidgetIds = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        initFirebaseDatabase();

    }

    @Override
    public void onDataSetChanged() {
        initFirebaseDatabase();
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
        initFirebaseDatabase();
        URL url = null;
        Bitmap image = null;
        TopBooks book = topBooksList.get(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        try {
            url = new URL(book.getImage());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        remoteViews.setTextViewText(R.id.widgetTitle, book.getTitle());
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

    private void initFirebaseDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FIREBASE_DATABASE_PATH);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooksList.add(0, topBooks);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
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
