package com.project.agunay.domain;

public class Achievement {

    private String id;
    private String description;
    private byte[] picture;
    private String title;
    public Achievement() {
    }
    public Achievement(String id, String description, byte[] picture, String title) {
        this.description = description;
        this.picture = picture;
        this.title = title;
        this.id= id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
