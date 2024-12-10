package com.project.agunay.domain;

import java.util.ArrayList;
import java.util.List;

public class Quizz {
    private String name;
    private List<Question> questions;
    private List<Question> wrongQuestions;
    private List<Question> correctQuestions;
    private int totalQuestions;
    private int correctAnswers;

    private byte[] picture;
    public Quizz() {
        this.correctAnswers = 0;
        this.correctQuestions = new ArrayList<>();
        this.wrongQuestions = new ArrayList<>();
    }

    public Quizz(String name, List<Question> questions) {
        this();
        this.name = name;
        this.questions = questions;
        this.totalQuestions = questions != null ? questions.size() : 0;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public List<Question> getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(List<Question> correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public List<Question> getWrongQuestions() {
        return wrongQuestions;
    }

    public void setWrongQuestions(List<Question> wrongQuestions) {
        this.wrongQuestions = wrongQuestions;
    }
}
