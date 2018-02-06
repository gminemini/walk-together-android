package com.custu.project.walktogether.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.google.gson.Gson;

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
