package com.worna.sportoo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public Integer gender;
    public String phoneNumber;
    public String email;
    public String password;
    public Date inscriptionDate;
    public Boolean isManager;
    public Boolean isInstructor;
    public String language ;
    public String address;
    public String cityName;
    public int zipCode;
    public String country;

    public Customer(String firstName, String lastName, Date birthDate, Integer gender, String phoneNumber, String email, String password, Date inscriptionDate, Boolean isManager, Boolean isInstructor, String language, String address, String cityName, int zipCode, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.inscriptionDate = inscriptionDate;
        this.isManager = isManager;
        this.isInstructor = isInstructor;
        this.language = language;
        this.address = address;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.country = country;
    }
}

