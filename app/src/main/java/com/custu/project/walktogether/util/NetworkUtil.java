package com.custu.project.walktogether.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by suparuch on 8/9/17.
 */

public class NetworkUtil {

    public static boolean isOnline(Context context, View view) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            return true;
        } else {
            Snackbar.make(view, "กรุณาตรวจสอบการเชื่อมต่อเครือข่าย", Snackbar.LENGTH_LONG).show();
            return false;
        }
    }
}
