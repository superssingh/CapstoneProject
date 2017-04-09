package com.santossingh.capstoneproject.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    int appWidgetId;

    private WidgetDataProvider widgetDataProvider;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        WidgetDataProvider widgetDataProvider = new WidgetDataProvider(this, intent);
        return widgetDataProvider;
    }

}
