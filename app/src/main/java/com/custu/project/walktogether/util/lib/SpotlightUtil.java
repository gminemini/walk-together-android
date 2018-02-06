package com.custu.project.walktogether.util.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.view.animation.DecelerateInterpolator;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.LoginActivity;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;


class SpotlightUtil {

    public static void isOnline(Context context) {
       /* SimpleTarget simpleTarget = new SimpleTarget.Builder()
                .setPoint(100f, 340f) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
                .setRadius(80f) // radius of the Target
                .setTitle("the title") // title
                .setDescription("the description") // description
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        // do something
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        // do something
                    }
                })
                .build();*/

       /* Spotlight.with(this)
                .setOverlayColor(ContextCompat.getColor(context, R.color.background)) // background overlay color
                .setDuration(100) // duration of Spotlight emerging and disappearing in ms
                .setAnimation(new DecelerateInterpolator(2f)) // animation of Spotlight
                .setTargets(simpleTarget) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() { // callback when Spotlight starts
                    @Override
                    public void onStarted() {
                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() { // callback when Spotlight ends
                    @Override
                    public void onEnded() {
                    }
                })
                .start();*/
    }
}
