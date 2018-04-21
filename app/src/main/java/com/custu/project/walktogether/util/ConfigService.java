package com.custu.project.walktogether.util;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class ConfigService {
    //public static final String BASE_URL = "http://10.0.2.2:8181/api/v1/";
    public static final String BASE_URL = "http://159.65.128.189:8181/api/v1/";
    public static final String BASE_URL_IMAGE = "http://159.65.128.189:8181/";

    //Google MAp API Key
    public static final String GOOGLE_API_KEY = "AIzaSyButAs0BAr5u7HX75zLiwr9rqWVSRTCFmc";

    //Log
    public static final String LOG_KEY = "4257bd93-053b-489e-aba5-19abb78fcc36";

    //Master
    public static final String SEX = "sex/";
    public static final String EDUCATION = "education/";
    public static final String PROVINCE = "province/";
    public static final String DISTRICT = "district/by-id-province/";
    public static final String SUB_DISTRICT = "sub-district/by-id-district/";


    //Evaluation
    public static final String EVALUATION = "evaluation/";
    public static final String EVALUATION_RANDOM = "random/";
    public static final String EVALUATION_CHECK = "check-evaluation/";

    //Login
    public static final String LOGIN = "login";
    public static final String FORGET_PASSWORD = "forget-password/";

    //Forget Password
    public static final String FORGET_PASSWORD_EMAIL = "forget-password-email/";
    public static final String FORGET_PASSWORD_TELL = "forget-password-tell/";

    //Caretaker
    public static final String CARETAKER = "caretaker/";
    public static final String CARETAKER_BY_NUMBER = "caretaker/by-caretaker-number/";

    //Patient
    public static final String PATIENT = "patient/";
    public static final String PATIENT_BY_NUMBER = "by-patient-number/";
    public static final String HISTORY_EVALUATION = "history-evaluation/";

    //Matching
    public static final String MATCHING = "matching/";
    public static final String MATCHING_CARETAKER_UNDER_PATIENT = "caretaker-by-patient/";
    public static final String MATCHING_PATIENT_UNDER_CARETAKER = "patient-by-caretaker/";
    public static final String MATCHING_ADD_CARETAKER = "add-caretaker/";
    public static final String MATCHING_ADD_PATIENT = "add-patient/";
    public static final String MATCHING_REMOVE_PATIENT = "remove-patient/";
    public static final String MATCHING_PATIENT_NUMBER = "?patientNumber=";
    public static final String MATCHING_REMOVE_CARETAKER = "remove-caretaker/";
    public static final String MATCHING_CARETAKER_NUMBER = "?caretakerNumber=";

    //Upload Image
    public static final String UPLOAD_IMAGE = "upload-image/";

    //SMS API
    public static final String SMS_API_BASE = "http://www.thsms.com/api/";
    public static final String SMS_API = "rest?method=send&username=punnoket&password=e6eb8f&from=0000&to=";
    public static final String SMS_MESSAGE = "&message=";

    //Mission
    public static final String MISSION = "mission/";
    public static final String MAP_ALL = "all-map/";

    public static final long TIME_INTERVAL = 31000;

    public static final double DEFAULT_LAT = 13.736717;
    public static final double DEFAULT_LONG = 100.523186;
}
