package com.custu.project.walktogether.data.Evaluation;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class Tmse {
    private String category;
    private ArrayList<NumberQuestion> numberQuestions;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<NumberQuestion> getNumberQuestions() {
        return numberQuestions;
    }

    public void setNumberQuestions(ArrayList<NumberQuestion> numberQuestions) {
        this.numberQuestions = numberQuestions;
    }
}
