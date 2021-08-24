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
    public Integer id;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public Integer gender;
    public String phoneNumber;
    public String email;
    public String password;
    public Date inscriptionDate;
    public Integer isManager;
    public Integer isInstructor;
    public String language ;
    public String address;
    public String cityName;
    public Integer zipCode;
    public String country;

    public Customer(String firstName, String lastName, Date birthDate, Integer gender, String phoneNumber, String email, String password, Date inscriptionDate, Integer isManager, Integer isInstructor, String language, String address, String cityName, Integer zipCode, String country) {
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

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", isManager=" + isManager +
                ", isInstructor=" + isInstructor +
                ", language='" + language + '\'' +
                ", address='" + address + '\'' +
                ", cityName='" + cityName + '\'' +
                ", zipCode=" + zipCode +
                ", country='" + country + '\'' +
                '}';
    }
}

