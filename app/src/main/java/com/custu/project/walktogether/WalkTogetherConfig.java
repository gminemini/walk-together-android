package com.custu.project.walktogether;

import android.app.Application;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.ConfigService;
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
        ConfigService.detectingBuild();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CSChatThaiUI.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
