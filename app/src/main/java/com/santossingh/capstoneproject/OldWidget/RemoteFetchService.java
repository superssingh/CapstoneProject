//package com.santossingh.capstoneproject.OldWidget;
//
//import android.app.Service;
//import android.appwidget.AppWidgetManager;
//import android.content.Intent;
//import android.os.IBinder;
//
///**
// * Created by santoshsingh on 02/04/17.
// */
//
//
//public class RemoteFetchService extends Service {
//    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
//            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//        }
//        populateWidget();
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private void populateWidget() {
//        Intent widgetUpdateIntent = new Intent();
//        widgetUpdateIntent.setAction(WidgetProvider.DATA_FETCHED);
//        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        sendBroadcast(widgetUpdateIntent);
//        this.stopSelf();
//    }
//}
