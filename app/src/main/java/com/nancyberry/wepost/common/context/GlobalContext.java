package com.nancyberry.wepost.common.context;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by nan.zhang on 3/30/16.
 */
public class GlobalContext extends Application {
    public static final String TAG = GlobalContext.class.getSimpleName();
    private static GlobalContext sContext;
    public String CLIENT_ID;
    public String CLIENT_SECRET;
    public String REDIRECT_URI;
    public String BASE_URI;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
//        SettingUtils.loadSettings();

        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            CLIENT_ID = bundle.getString("CLIENT_ID");
            CLIENT_SECRET = bundle.getString("CLIENT_SECRET");
            REDIRECT_URI = bundle.getString("REDIRECT_URI");
            BASE_URI = bundle.getString("BASE_URI");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        }
    }

    public static GlobalContext getInstance() {
        return sContext;
    }
}
