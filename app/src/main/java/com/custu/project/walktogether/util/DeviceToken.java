package com.custu.project.walktogether.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.google.firebase.iid.FirebaseInstanceId;

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

    public String getToken(Context context) {
        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            return token;
        } catch (Exception e) {
            return "";
        }
    }
}
