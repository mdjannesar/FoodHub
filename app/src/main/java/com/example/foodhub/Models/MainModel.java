package com.example.foodhub.Models;

public class MainModel {
    int image;
    String name,price,decription;

    public MainModel(int image, String name, String price, String decription) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.decription = decription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
}
