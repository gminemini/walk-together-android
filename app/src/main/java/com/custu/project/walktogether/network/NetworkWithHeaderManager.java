package com.custu.project.walktogether.network;

import com.custu.project.walktogether.util.ConfigService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Pannawat on 18/07/2017.
 */

public class NetworkWithHeaderManager {

    public static <S> S createService(Class<S> serviceClass) {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigService.SMS_API_BASE)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }
}
