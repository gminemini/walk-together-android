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
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
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
