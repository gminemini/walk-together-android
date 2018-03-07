package com.custu.project.walktogether.data.historyevaluation;

import com.custu.project.walktogether.data.Evaluation.Question;

/**
 * Created by pannawatnokket on 3/3/2018 AD.
 */

public class PatientTest {

    private Long id;
    private Question questionEvaluation;
    private String answer;
    private String score;
    private String evaluationCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestionEvaluation() {
        return questionEvaluation;
    }

    public void setQuestionEvaluation(Question questionEvaluation) {
        this.questionEvaluation = questionEvaluation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvaluationCategory() {
        return evaluationCategory;
    }

    public void setEvaluationCategory(String evaluationCategory) {
        this.evaluationCategory = evaluationCategory;
    }
}
