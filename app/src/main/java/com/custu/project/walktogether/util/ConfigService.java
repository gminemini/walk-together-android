package com.custu.project.walktogether.util;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class ConfigService {
    public static final String BASE_URL = "http://10.0.2.2:8181/api/v1/";
   // public static final String BASE_URL = "http://159.65.10.67:8181/api/v1/";

    //Master
    public static final String SEX = "sex/";
    public static final String PROVINCE = "province/";
    public static final String DISTRICT = "district/by-id-province/";
    public static final String SUB_DISTRICT = "sub-district/by-id-district/";


    //Evaluation
    public static final String EVALUATION = "evaluation/";
    public static final String EVALUATION_RANDOM = "random/";
    public static final String EVALUATION_CHECK = "check-evaluation/";

    //Login
    public static final String LOGIN = "login";

    //Forget Password
    public static final String FORGET_PASSWORD_EMAIL = "forget-password-email/";
    public static final String FORGET_PASSWORD_TELL = "forget-password-tell/";

    //Caretaker
    public static final String CARETAKER = "caretaker/";

    //Patient
    public static final String PATIENT = "patient/";
    public static final String HISTORY_EVALUATION = "history-evaluation/";

    //Matching
    public static final String MATCHING = "matching/";
    public static final String MATCHING_CARETAKER_UNDER_PATIENT = "caretaker-by-patient/";
    public static final String MATCHING_PATIENT_UNDER_CARETAKER = "patient-by-caretaker/";
    public static final String MATCHING_ADD_CARETAKER = "add-caretaker/";
    public static final String MATCHING_ADD_PATIENT = "add-patient/";
    public static final String MATCHING_REMOVE_PATIENT = "remove-patient/";
    public static final String MATCHING_PATIENT_NUMBER = "?patientNumber=";
    public static final String MATCHING_REMOVE_CARETAKER = "add-patient/";
    public static final String MATCHING_CARETAKER_NUMBER = "?caretakerNumber=";


}
