package com.nancyberry.wepost.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nancyberry.wepost.common.context.GlobalContext;

/**
 * Created by nan.zhang on 4/7/16.
 */
public class SystemUtils {
    private static int screenWidth;

    private static int screenHeight;

    private static float density;

    private static void getScreenInfo() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) GlobalContext.getInstance().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        density = displayMetrics.density;
    }

    public static int getScreenWidth() {
        if (screenWidth == 0) {
            getScreenInfo();
        }
        return screenWidth;
    }

    public static int getScreenHeight() {
        if (screenHeight == 0) {
            getScreenHeight();
        }
        return screenHeight;
    }

    public static float getDensity() {
        if (density == 0.0f) {
            getDensity();
        }
        return density;
    }

    public static int dip2px(int dipValue) {
        float reSize = GlobalContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }
}
