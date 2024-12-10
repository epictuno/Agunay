package com.project.agunay.domain;

import java.util.List;
public class Question {
    private String questionTitle;
    private String questionType;
    private List<Answer> answers;

    public Question() {
    }

    // Constructor con parámetros
    public Question(String questionTitle, String questionType, List<Answer> answers) {
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.answers = answers;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
