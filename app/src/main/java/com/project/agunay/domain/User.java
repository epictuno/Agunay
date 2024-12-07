package com.project.agunay.domain;

import java.util.List;
import java.util.Map;

public class User {
    private String id;
    private int points;
    private List<Achievement> achievements;
    private String email;
    private Map<ShopItem, Integer> inventory;
    private byte[] profilePicture;
    private String username;
    private String password;
    public User(String email, String password, String username) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String password, String username, String id, int points, List<Achievement> achievements, Map<ShopItem, Integer> inventory, byte[] profilePicture) {
        this(email,password,username);
        this.id = id;
        this.points = points;
        this.achievements = achievements;
        this.inventory = inventory;
        this.profilePicture = profilePicture;
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

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<ShopItem, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<ShopItem, Integer> inventory) {
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
