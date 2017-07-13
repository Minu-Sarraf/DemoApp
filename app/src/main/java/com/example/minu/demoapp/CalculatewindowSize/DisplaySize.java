package com.example.minu.demoapp.CalculatewindowSize;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by minu on 7/11/2017.
 */

public class DisplaySize {
    public static Point getDisplaySize(Context context) {
        Point size = new Point();
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        size = new Point(width, height);
        return size;
    }
}
