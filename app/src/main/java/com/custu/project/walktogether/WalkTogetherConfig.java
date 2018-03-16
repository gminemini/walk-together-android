package com.custu.project.walktogether;

import android.app.Application;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.UserManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pannawatnokket on 16/3/2018 AD.
 */

public class WalkTogetherConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
        isFirstInstall();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SukhumvitSet.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void isFirstInstall() {
        if (!UserManager.getInstance(this).isFirstInstall()) {
            UserManager.getInstance(this).removePatient();
            UserManager.getInstance(this).removeCaretaker();
            UserManager.getInstance(this).setInstall();
        }
    }
}
