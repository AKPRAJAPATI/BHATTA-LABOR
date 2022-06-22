package com.example.bhattausers.Models;

public class registerModel {
    String imageUrl ;
    String name;
    String email;
    String password;
    String phone;
    String userId;
    String AdminAuthId;


    public registerModel() {
    }

    public registerModel(String imageUrl, String name, String email, String password, String phone, String userId) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userId = userId;
    }

    public registerModel(String imageUrl, String name, String email, String password, String phone, String userId, String adminAuthId) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userId = userId;
        AdminAuthId = adminAuthId;
    }

    public String getAdminAuthId() {
        return AdminAuthId;
    }

    public void setAdminAuthId(String adminAuthId) {
        AdminAuthId = adminAuthId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
