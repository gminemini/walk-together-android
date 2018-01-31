package com.custu.project.walktogether;

import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Test;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class ConnectApiTest {

    @Test
    public void registerPatient() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", "punnoketTest");
        jsonObject.addProperty("password","1234");
        jsonObject.addProperty("titleName","นาย");
        jsonObject.addProperty("firstName","ปัณวรรธน์");
        jsonObject.addProperty("lastName","นกเกตุ");
        jsonObject.addProperty("sexId",1);
        jsonObject.addProperty("dob","21 มีนาคม 2539");
        jsonObject.addProperty("address","60 ซอย6");
        jsonObject.addProperty("provinceId",233);
        jsonObject.addProperty("districtId",23);
        jsonObject.addProperty("subDistrictId",45);
        jsonObject.addProperty("tell","09843848");
        jsonObject.addProperty("occupation","ผอ.");
        jsonObject.addProperty("email","goku.pun@hotmail.com");

        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                Assert.assertNotNull(object);
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, "patient", jsonObject);

    }
}
