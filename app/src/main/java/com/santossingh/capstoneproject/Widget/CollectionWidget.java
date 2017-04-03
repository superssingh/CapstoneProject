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

/**
 * Implementation of App Widget functionality.
 */
public class CollectionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);

        // Set up the collection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views);
        } else {
            setRemoteAdapterV11(context, views);
        }

//        // Instruct the widget manager to update the widget
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

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */

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
//        configIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, configIntent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);
        views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
//        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//
////        Intent configIntent = new Intent(context, MainActivity.class);
////        configIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, configIntent, 0);
////        views.setOnClickPendingIntent(R.id.widget_list, pendingIntent);
//
////        //retrieve a ref to the manager so we can pass a view update
////
//        Intent configIntent = new Intent(context, MainActivity.class);
////        configIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 1, configIntent, 0);
////        //intent to start service
////
////        // Get the layout for the App Widget
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
////
////        //attach the click listener for the service start command intent
//        views.setOnClickPendingIntent(R.id.widgetImage, configPendingIntent);
////
//        //define the componenet for self
//        ComponentName comp = new ComponentName(context.getPackageName(), WidgetService.class.getName());
////
////        //tell the manager to update all instances of the toggle widget with the click listener
//        mgr.updateAppWidget(comp, views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

