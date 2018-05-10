package com.custu.project.walktogether.util.lib;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class ButtonClickAlpha {

    private static ButtonClickAlpha instance;
    private static AlphaAnimation alphaAnimation;

    public static ButtonClickAlpha getInstance() {
        if (instance == null) {
            instance = new ButtonClickAlpha();
            alphaAnimation = new AlphaAnimation(1F, 0.7F);
        }
        return instance;
    }
    public void setAlphaAnimation(View view) {
        view.startAnimation(alphaAnimation);
    }
}
