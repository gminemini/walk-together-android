package com.custu.project.walktogether.network;

import android.os.Build;

import com.custu.project.project.walktogether.BuildConfig;
import com.custu.project.walktogether.util.ConfigService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Pannawat on 11/07/2017.
 */

public class NetworkManager {
    public Retrofit retrofit;

    public NetworkManager() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        String authToken = Credentials.basic(ConfigService.USERNAME, ConfigService.PASSWORD);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("User-Agent", "ANDROID " + currentVersion())
                            .header("Authorization", authToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                })
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging);
            client = builder.build();
        } else {
            client = builder
                    .build();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(ConfigService.BASE_URL)
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private String currentVersion() {
        double release = Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)", "$1"));
        String codeName = "Unsupported";//below Jelly bean OR above Oreo
        if (release >= 4.1 && release < 4.4) codeName = "Jelly Bean";
        else if (release < 5) codeName = "Kit Kat";
        else if (release < 6) codeName = "Lollipop";
        else if (release < 7) codeName = "Marshmallow";
        else if (release < 8) codeName = "Nougat";
        else if (release < 9) codeName = "Oreo";
        return codeName + " v" + release + ", API Level: " + Build.VERSION.SDK_INT;
    }
}
