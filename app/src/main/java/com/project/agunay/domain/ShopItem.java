package com.project.agunay.domain;

public class ShopItem {
    private String id;
    private String name;
    private String description;
    private int price;
    private byte[] image;

    public ShopItem() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.price = 0;
        this.image = new byte[0];
    }

    public ShopItem(String id, String name, int price, byte[] image, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
