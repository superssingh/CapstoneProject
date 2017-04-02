//package com.santossingh.capstoneproject.OldWidget;
//
//
//import android.app.Activity;
//import android.appwidget.AppWidgetManager;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.santossingh.capstoneproject.R;
//
//public class ConfigActivity extends AppCompatActivity {
//
//    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_config);
//
//        assignAppWidgetId();
//        startWidget();
//    }
//
//    /**
//     * Widget configuration activity,always receives appwidget Id appWidget Id =
//     * unique id that identifies your widget analogy : same as setting view id
//     * via @+id/viewname on layout but appwidget id is assigned by the system
//     * itself
//     */
//    private void assignAppWidgetId() {
//        Bundle extras = getIntent().getExtras();
//        if (extras != null)
//            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//    }
//
//    /**
//     * This method right now displays the widget and starts a Service to fetch
//     * remote data from Server
//     */
//    private void startWidget() {
//
//        // this intent is essential to show the widget
//        // if this intent is not included,you can't show
//        // widget on homescreen
//        Intent intent = new Intent();
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        setResult(Activity.RESULT_OK, intent);
//
//        // start your service
//        // to fetch data from web
//        Intent serviceIntent = new Intent(this, ListProvider.class);
//        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        startService(serviceIntent);
//
//        // finish this activity
//        this.finish();
//
//    }
//
//}