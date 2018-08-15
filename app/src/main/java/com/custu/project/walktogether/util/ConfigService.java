package com.custu.project.walktogether.util;

import com.custu.project.project.walktogether.BuildConfig;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class ConfigService {
    //public static final String BASE_URL = "http://10.0.2.2:8181/api/v1/";
    public static final String BASE_URL = "http://159.65.128.189:8181/api/v1/";
    public static final String BASE_URL_IMAGE = "http://159.65.128.189:8181/";
    public static final String USERNAME = "walk-together-api";
    public static final String PASSWORD = "72SpIhczsAIMNsa8wuBGX35h5i18XA";

    //Google MAp API Key
    public static String GOOGLE_API_KEY = null;

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
    public static final String CARETAKER_CHANGE_PASSWORD = "caretaker/change-password/";

    //Patient
    public static final String PATIENT = "patient/";
    public static final String PATIENT_BY_NUMBER = "by-patient-number/";
    public static final String HISTORY_EVALUATION = "history-evaluation/";
    public static final String PATIENT_CHANGE_PASSWORD = "patient/change-password/";

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
    public static final String MISSION_SEND = "send/";
    public static final String HISTORY_MISSION = "history-by-id//";
    public static final String DUMMY_CHOICE = "answer-by-type/";
    public static final String MISSION_ID = "by-id/";
    public static final String DIRECTION = "mission/direction/";

    //Reward
    public static final String RANDOM_REWARD = "collection/reward-by-level/";

    //Collection
    public static final String COLLECTION_RANGE = "?range=";
    public static final String COLLECTION_BY_PATIENT = "collection/reward-by-patient/";

    //Constant
    public static final long TIME_INTERVAL = 31000;
    public static final long TIME = 31;
    public static final double DEFAULT_LAT = 13.736717;
    public static final double DEFAULT_LONG = 100.523186;
    public static final double RADIUS_MISSION = 4;
    public static final double AVERAGE_OPENCV = 99.95552247764787;
    public static final double MIN_OPENCV = 99.94820573199957;

    public static void detectingBuild() {
        if (BuildConfig.DEBUG) {
            //Debug build
            GOOGLE_API_KEY = "AIzaSyD_gTByKJ8of3h6TqsHur4R0E5hfMCcgBg";
        } else {
            //Release
            GOOGLE_API_KEY = "AIzaSyCqCrvJKf3g95PMQL6QsHxby7190-XZFqc";
        }
    }

}
