package com.example.swapzy;

public class User {
    String name,email,phone,pass,key;

    public User(String name, String email, String phone, String pass, String key) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.key = key;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPass() {
        return pass;
    }

    public String getKey() {
        return key;
    }
}
