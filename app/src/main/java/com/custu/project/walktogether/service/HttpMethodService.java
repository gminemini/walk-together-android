package com.custu.project.walktogether.service;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Pannawat on 11/07/2017.
 */

public interface HttpMethodService {
    @GET
    Call<JsonObject> get(@Url String url);

    @GET
    Call<ResponseBody> getXML(@Url String url);

    @POST
    Call<JsonObject> post(@Url String url, @Body RequestBody body);

    @Multipart
    @POST()
    Call<JsonObject> uploadImageProfile(@Url String url, @Part MultipartBody.Part image, @Part MultipartBody.Part data);
    
    @PATCH
    Call<JsonObject> patch(@Url String url, @Body RequestBody user);

    @DELETE
    Call<JsonObject> delete(@Url String url);
}
