package com.custu.project.walktogether.model;

import com.custu.project.walktogether.manager.ConnectServer;
import com.google.gson.JsonObject;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class PatientModel {
    private static PatientModel instance;

    public static PatientModel getInstance() {
        if (instance == null)
            instance = new PatientModel();
        return instance;
    }

    public  void register() {


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
    }
}
