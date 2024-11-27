package com.project.agunay.domain;

import java.util.List;

public class Quizz {
    private String name;
    private List<Question> questions;
    private List<Question> wrongQuestions;
    private List<Question> correctQuestions;
    private int totalQuestions;
    private int correctAnswers;
    public Quizz() {
    }

    public Quizz(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.totalQuestions = questions != null ? questions.size() : 0;
        this.correctAnswers = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.totalQuestions = questions != null ? questions.size() : 0;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
