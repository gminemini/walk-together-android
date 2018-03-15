package com.custu.project.walktogether.data.historyevaluation;

/**
 * Created by pannawatnokket on 3/3/2018 AD.
 */

public class HistoryEvaluation {
    private Long id;
    private EvaluationTest evaluationTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluationTest getEvaluationTest() {
        return evaluationTest;
    }

    public void setEvaluationTest(EvaluationTest evaluationTest) {
        this.evaluationTest = evaluationTest;
    }
}
