package com.atandi.smarttravel.Models;

public class User {

    String username;
    String userphone;
    String userpassword;

    public User() {
    }

    public User(String username, String userphone, String userpassword) {
        this.username = username;
        this.userphone = userphone;
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
