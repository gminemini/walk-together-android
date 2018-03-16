package com.custu.project.walktogether.util;

import android.content.Context;
import android.widget.ImageView;

import com.custu.project.project.walktogether.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by pannawatnokket on 9/2/2018 AD.
 */

public class PicassoUtil {
    private static PicassoUtil instance;

    public static PicassoUtil getInstance() {
        if (instance == null)
            instance = new PicassoUtil();
        return instance;
    }

    public void setImageProfile(Context context, String path, ImageView imageView) {
        if (path != null) {
            Picasso.with(context).invalidate(ConfigService.BASE_URL_IMAGE + path);
            Picasso.with(context)
                    .load(ConfigService.BASE_URL_IMAGE + path)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);

        } else {
            Picasso.with(context)
                    .load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }

    }

    public void setImage(Context context, String path, ImageView imageView) {
        if (path != null) {
            Picasso.with(context).invalidate(ConfigService.BASE_URL_IMAGE + path);
            Picasso.with(context)
                    .load(ConfigService.BASE_URL_IMAGE + path)
                    .placeholder(R.drawable.loading_gear)
                    .error(R.drawable.image_not_found)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);

        } else {
            Picasso.with(context)
                    .load(R.drawable.loading_gear)
                    .placeholder(R.drawable.loading_gear)
                    .error(R.drawable.image_not_found)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }

    }
}
