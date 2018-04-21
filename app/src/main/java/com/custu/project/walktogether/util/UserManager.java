package com.custu.project.walktogether.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Evaluation.Tmse;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.mission.Mission;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pannawatnokket on 7/2/2018 AD.
 */

public class UserManager {

    private static UserManager instance;
    private SharedPreferences sharedPreferences;

    public static UserManager getInstance(Context context) {
        if (instance == null)
            instance = new UserManager(context);
        return instance;
    }

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences("wtPref", MODE_PRIVATE);
    }

    private SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getPrefsEditor() {
        return sharedPreferences.edit();
    }

    public void storeMission(ArrayList<Mission> missionArrayList) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString("mission", new Gson().toJson(missionArrayList));
        editor.apply();
    }

    public ArrayList<Mission> getMission() {
        String json = getSharedPreferences().getString("mission", "");
        Type type = new TypeToken<ArrayList<Mission>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public void storeTMSE(ArrayList<Tmse> tmseArrayList) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString("tmse", new Gson().toJson(tmseArrayList));
        editor.apply();
    }

    public ArrayList<Tmse> getTMSE() {
        String json = getSharedPreferences().getString("tmse", "");
        Type type = new TypeToken<ArrayList<Tmse>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public void setInstall() {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putBoolean("isInstall", true);
        editor.apply();
    }

    public void storeCaretaker(Caretaker caretaker) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString("caretaker", new Gson().toJson(caretaker));
        editor.apply();
    }

    public void storePatient(Patient patient) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString("patient", new Gson().toJson(patient));
        editor.apply();
    }

    public Caretaker getCaretaker() {
        String json = getSharedPreferences().getString("caretaker", "");
        return new Gson().fromJson(json, Caretaker.class);
    }

    public boolean isFirstInstall() {
        return getSharedPreferences().getBoolean("isInstall", false);
    }

    public Patient getPatient() {
        String json = getSharedPreferences().getString("patient", "");
        return new Gson().fromJson(json, Patient.class);
    }

    public void removeCaretaker() {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.remove("caretaker");
        editor.apply();
    }

    public void removePatient() {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.remove("patient");
        editor.apply();
    }
}
