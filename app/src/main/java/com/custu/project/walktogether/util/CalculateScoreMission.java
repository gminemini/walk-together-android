package com.custu.project.walktogether.util;

/**
 * Created by pannawatnokket on 18/3/2018 AD.
 */

public class CalculateScoreMission {

    private static CalculateScoreMission instance;

    public static CalculateScoreMission getInstance() {
        if (instance == null) {
            instance = new CalculateScoreMission();
        }
        return instance;
    }

    public double getScore(int resultTime, int maxScore, int maxTime) {
        return ((resultTime * maxScore) / maxTime);
    }
}
