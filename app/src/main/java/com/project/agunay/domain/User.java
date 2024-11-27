package com.project.agunay.domain;

import java.util.List;

public class User {
    private String id;
    private int points;
    private List<Achievement> achievements;
    private String email;
    private List<ShopItem> inventory;
    private byte[] profilePicture;
    private String username;

    public User() {
    }

    public User(String id, int points, List<Achievement> achievements, String email, List<ShopItem> inventory, byte[] profilePicture, String username) {
        this.id = id;
        this.points = points;
        this.achievements = achievements;
        this.email = email;
        this.inventory = inventory;
        this.profilePicture = profilePicture;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ShopItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<ShopItem> inventory) {
        this.inventory = inventory;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
