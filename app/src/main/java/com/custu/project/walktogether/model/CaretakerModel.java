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
