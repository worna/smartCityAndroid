package com.worna.sportoo.models;

import androidx.room.Entity;

@Entity
public class Login {
    public String email;
    public String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

