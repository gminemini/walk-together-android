package com.custu.project.walktogether.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by pannawatnokket on 4/2/2018 AD.
 */

public class ProgressDialogCustom {

    private ProgressDialog progressDialog;

    private static ProgressDialogCustom instance;

    public static ProgressDialogCustom getInstance(Context context) {
        if (instance == null) {
            instance = new ProgressDialogCustom(context);
        }
        return instance;
    }

    private ProgressDialogCustom(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    public void dismiss() {
        progressDialog.dismiss();
    }

    public void show() {
        progressDialog.show();
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }

    public void cancel() {
        progressDialog.cancel();
    }

}
