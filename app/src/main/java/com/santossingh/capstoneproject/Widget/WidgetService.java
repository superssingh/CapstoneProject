package com.santossingh.capstoneproject.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by santoshsingh on 02/04/17.
 */

public class WidgetService extends RemoteViewsService {
    int appWidgetId;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
