package com.custu.project.walktogether.model;

import android.util.Log;

import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.master.District;
import com.custu.project.walktogether.data.master.Province;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.data.master.SubDistrict;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class CaretakerModel {
    private static CaretakerModel instance;

    public static CaretakerModel getInstance() {
        if (instance == null)
            instance = new CaretakerModel();
        return instance;
    }

    public void register() {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", "punnoketTest");
        jsonObject.addProperty("password", "1234");
        jsonObject.addProperty("titleName", "นาย");
        jsonObject.addProperty("firstName", "ปัณวรรธน์");
        jsonObject.addProperty("lastName", "นกเกตุ");
        jsonObject.addProperty("sexId", 1);
        jsonObject.addProperty("dob", "21 มีนาคม 2539");
        jsonObject.addProperty("address", "60 ซอย6");
        jsonObject.addProperty("provinceId", 233);
        jsonObject.addProperty("districtId", 23);
        jsonObject.addProperty("subDistrictId", 45);
        jsonObject.addProperty("tell", "09843848");
        jsonObject.addProperty("occupation", "ผอ.");
        jsonObject.addProperty("email", "goku.pun@hotmail.com");
    }

    public Caretaker getCaretaker(JsonObject data) {
        Sex sex = new Gson().fromJson(data.get("data").getAsJsonObject().get("sex"), Sex.class);
        Province province = new Gson().fromJson(data.get("data").getAsJsonObject().get("province"), Province.class);
        District district = new Gson().fromJson(data.get("data").getAsJsonObject().get("district"), District.class);
        SubDistrict subDistrict = new Gson().fromJson(data.get("data").getAsJsonObject().get("subDistrict"), SubDistrict.class);

        Caretaker caretaker = new Gson().fromJson(data.get("data").getAsJsonObject(), Caretaker.class);
        caretaker.setSex(sex);
        caretaker.setProvince(province);
        caretaker.setDistrict(district);
        caretaker.setSubDistrict(subDistrict);

        return caretaker;
    }

    public ArrayList<Caretaker> getListCaretaker(JsonObject data) {
        JsonArray jsonArray = data.getAsJsonArray("data");
        ArrayList<Caretaker> caretakerArrayList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
           JsonObject jsonObject =  jsonArray.get(i).getAsJsonObject();
            Sex sex = new Gson().fromJson(jsonObject.getAsJsonObject("sex"), Sex.class);
            Province province = new Gson().fromJson(jsonObject.getAsJsonObject("province"), Province.class);
            District district = new Gson().fromJson(jsonObject.getAsJsonObject("district"), District.class);
            SubDistrict subDistrict = new Gson().fromJson(jsonObject.getAsJsonObject("subDistrict"), SubDistrict.class);

            Caretaker caretaker = new Gson().fromJson(jsonObject.getAsJsonObject(), Caretaker.class);
            caretaker.setSex(sex);
            caretaker.setProvince(province);
            caretaker.setDistrict(district);
            caretaker.setSubDistrict(subDistrict);
            caretakerArrayList.add(caretaker);
        }
        return caretakerArrayList;
    }

}
