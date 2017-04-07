package com.santossingh.capstoneproject.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.santossingh.capstoneproject.Activities.WidgetActivity;
import com.santossingh.capstoneproject.R;

public class CollectionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);

        // Set up the collection
        setRemoteAdapter(context, views);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));
    }

    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, WidgetService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        Intent configIntent = new Intent(context, WidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, configIntent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);
        views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

