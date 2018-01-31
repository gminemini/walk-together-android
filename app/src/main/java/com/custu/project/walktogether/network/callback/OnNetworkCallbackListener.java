package com.custu.project.walktogether.network.callback;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Pannawat on 20/07/2017.
 */

public interface OnNetworkCallbackListener {
    void onResponse(JsonObject object, Retrofit retrofit);
    void onBodyError(ResponseBody responseBodyError);
    void onBodyErrorIsNull();
    void onFailure(Throwable t);
}
