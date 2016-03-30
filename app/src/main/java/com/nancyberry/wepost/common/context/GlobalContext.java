package com.nancyberry.wepost.common.context;

import android.app.Application;

import com.nancyberry.wepost.common.setting.SettingUtils;

/**
 * Created by nan.zhang on 3/30/16.
 */
public class GlobalContext extends Application {
    private static GlobalContext sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SettingUtils.loadSettings();
    }

    public static GlobalContext getInstance() {
        return sContext;
    }
}
