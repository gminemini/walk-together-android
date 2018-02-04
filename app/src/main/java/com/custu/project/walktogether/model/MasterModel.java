package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.Evaluation.Answer;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.data.Evaluation.Tmse;
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

public class MasterModel {
    private static MasterModel instance;

    public static MasterModel getInstance() {
        if (instance == null)
            instance = new MasterModel();
        return instance;
    }

    public ArrayList<Sex> getSex(JsonObject data) {
        ArrayList<Sex> sexArrayList = new ArrayList<>();
        JsonArray jsonArray = data.getAsJsonArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            sexArrayList.add(new Gson().fromJson(jsonArray.get(i).getAsJsonObject(), Sex.class));
        }
        return sexArrayList;
    }

    public ArrayList<Province> getProvince(JsonObject data) {
        ArrayList<Province> provinceArrayList = new ArrayList<>();
        JsonArray jsonArray = data.getAsJsonArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            provinceArrayList.add(new Gson().fromJson(jsonArray.get(i).getAsJsonObject(), Province.class));
        }
        return provinceArrayList;
    }

    public ArrayList<District> getDistrict(JsonObject data) {
        ArrayList<District> districtArrayList = new ArrayList<>();
        JsonArray jsonArray = data.getAsJsonArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            districtArrayList.add(new Gson().fromJson(jsonArray.get(i).getAsJsonObject(), District.class));
        }
        return districtArrayList;
    }

    public ArrayList<SubDistrict> getSubDistrict(JsonObject data) {
        ArrayList<SubDistrict> subDistrictArrayList = new ArrayList<>();
        JsonArray jsonArray = data.getAsJsonArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            subDistrictArrayList.add(new Gson().fromJson(jsonArray.get(i).getAsJsonObject(), SubDistrict.class));
        }
        return subDistrictArrayList;
    }

}
