package com.project.agunay.domain;

public class Answer {
    private String answerText;
    private boolean isAnswer;

    public Answer() {
    }

    public Answer(String answerText, boolean isAnswer) {
        this.answerText = answerText;
        this.isAnswer = isAnswer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }
}
