package com.custu.project.walktogether;

import android.app.Application;

import com.custu.project.project.walktogether.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pannawatnokket on 16/3/2018 AD.
 */

public class WalkTogetherConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SukhumvitSet.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
