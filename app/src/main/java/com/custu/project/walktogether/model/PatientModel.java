package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.master.District;
import com.custu.project.walktogether.data.master.Province;
import com.custu.project.walktogether.data.master.Sex;
import com.custu.project.walktogether.data.master.SubDistrict;
import com.custu.project.walktogether.manager.ConnectServer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

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


    public Patient getPatient(JsonObject data) {
        Sex sex = new Gson().fromJson(data.get("data").getAsJsonObject().get("sex"), Sex.class);
        Province province = new Gson().fromJson(data.get("data").getAsJsonObject().get("province"), Province.class);
        District district = new Gson().fromJson(data.get("data").getAsJsonObject().get("district"), District.class);
        SubDistrict subDistrict = new Gson().fromJson(data.get("data").getAsJsonObject().get("subDistrict"), SubDistrict.class);

        Patient patient = new Gson().fromJson(data.get("data").getAsJsonObject(), Patient.class);
        patient.setSex(sex);
        patient.setProvince(province);
        patient.setDistrict(district);
        patient.setSubDistrict(subDistrict);

        return patient;
    }

    public ArrayList<Patient> getListPatients(JsonObject data) {
        JsonArray jsonArray = data.getAsJsonArray("data");
        ArrayList<Patient> patientArrayList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject =  jsonArray.get(i).getAsJsonObject();
            Sex sex = new Gson().fromJson(jsonObject.getAsJsonObject("sex"), Sex.class);
            Province province = new Gson().fromJson(jsonObject.getAsJsonObject("province"), Province.class);
            District district = new Gson().fromJson(jsonObject.getAsJsonObject("district"), District.class);
            SubDistrict subDistrict = new Gson().fromJson(jsonObject.getAsJsonObject("subDistrict"), SubDistrict.class);

            Patient patient = new Gson().fromJson(jsonObject.getAsJsonObject(), Patient.class);
            patient.setSex(sex);
            patient.setProvince(province);
            patient.setDistrict(district);
            patient.setSubDistrict(subDistrict);
            patientArrayList.add(patient);
        }
        return patientArrayList;
    }

}
