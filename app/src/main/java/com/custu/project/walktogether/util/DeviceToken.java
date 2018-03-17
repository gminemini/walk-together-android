package com.custu.project.walktogether.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

/**
 * Created by pannawatnokket on 18/3/2018 AD.
 */

public class DeviceToken {

    private static DeviceToken instance;

    public static DeviceToken getInstance() {
        if (instance == null) {
            instance = new DeviceToken();
        }
        return instance;
    }

    @SuppressLint("HardwareIds")
    public String getToken(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
