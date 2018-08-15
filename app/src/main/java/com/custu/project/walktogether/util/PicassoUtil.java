package com.custu.project.walktogether.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.custu.project.project.walktogether.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;

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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic(ConfigService.USERNAME, ConfigService.PASSWORD);
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                }).build();
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        if (path != null) {
            picasso.invalidate(ConfigService.BASE_URL_IMAGE + path);
            picasso.load(ConfigService.BASE_URL_IMAGE + path)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);

        } else {
            picasso.load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }

    }

    public void setImage(Context context, String path, ImageView imageView) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic(ConfigService.USERNAME, ConfigService.PASSWORD);
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                }).build();
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        if (path != null) {
            picasso.invalidate(ConfigService.BASE_URL_IMAGE + path);
            picasso.load(ConfigService.BASE_URL_IMAGE + path)
                    .placeholder(R.drawable.loading_gear)
                    .error(R.drawable.image_not_found)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);

        } else {
            picasso.load(R.drawable.loading_gear)
                    .placeholder(R.drawable.loading_gear)
                    .error(R.drawable.image_not_found)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }

    }

    public void setImageNoCatch(Context context, String path, ImageView imageView) {
        String credential = Credentials.basic(ConfigService.USERNAME, ConfigService.PASSWORD);
        GlideUrl glideUrl = new GlideUrl(ConfigService.BASE_URL_IMAGE + path,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", credential)
                        .build());
        if (path != null) {
            Glide.with(context)
                    .load(glideUrl)
                    .into(imageView);

        }

    }
}
