package com.custu.project.walktogether.manager;

import com.custu.project.walktogether.network.NetworkManager;
import com.custu.project.walktogether.network.NetworkWithHeaderManager;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.service.HttpMethodService;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pannawat on 27/07/2017.
 */

public class ConnectServer extends NetworkManager {
    private HttpMethodService httpMethodService;

    private static ConnectServer instance;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private RequestBody body;

    private ConnectServer() {
        super();
    }

    public static ConnectServer getInstance() {
        if (instance == null)
            instance = new ConnectServer();
        return instance;
    }

    public static void setTestingInstance(ConnectServer newInstance) {
        instance = newInstance;
    }

    public void get(final OnDataSuccessListener listener, String url) {
        httpMethodService = retrofit.create(HttpMethodService.class);
        Call<JsonObject> call = httpMethodService.get(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if (object == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(object, retrofit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void post(final OnDataSuccessListener listener, String url, JsonObject bodyJson) {
        httpMethodService = retrofit.create(HttpMethodService.class);
        body = RequestBody.create(JSON, bodyJson.toString());
        Call<JsonObject> call = httpMethodService.post(url, body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if (object == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(object, retrofit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void sendSMS (final OnDataSuccessListener listener, String url) {
        httpMethodService = NetworkWithHeaderManager.createService(HttpMethodService.class);
        Call<ResponseBody> call = httpMethodService.getXML(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JsonObject jsonObject = new JsonObject();
                if (response.code() ==200) {
                    jsonObject.addProperty("status",response.code());
                    listener.onResponse(jsonObject, retrofit);
                } else {
                    jsonObject.addProperty("status",response.code());
                    listener.onResponse(jsonObject, retrofit);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void uploadImage(final OnDataSuccessListener listener, String url, String path, String idUser) {
        httpMethodService = retrofit.create(HttpMethodService.class);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part id = MultipartBody.Part.createFormData("id", idUser);

        Call<JsonObject> call = httpMethodService.uploadImageProfile(url, image, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if (object == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(object, retrofit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void update(final OnDataSuccessListener listener, String url, JsonObject bodyJson) {
        httpMethodService = retrofit.create(HttpMethodService.class);
        body = RequestBody.create(JSON, bodyJson.toString());
        Call<JsonObject> call = httpMethodService.patch(url, body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if (object == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(object, retrofit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void delete(final OnDataSuccessListener listener, String url) {
        httpMethodService = retrofit.create(HttpMethodService.class);
        Call<JsonObject> call = httpMethodService.delete(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if (object == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(object, retrofit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
