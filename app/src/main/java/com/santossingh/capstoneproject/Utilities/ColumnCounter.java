package com.santossingh.capstoneproject.Utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by santoshsingh on 26/03/17.
 */

public class ColumnCounter {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
