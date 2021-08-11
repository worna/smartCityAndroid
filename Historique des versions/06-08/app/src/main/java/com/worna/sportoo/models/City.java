package com.worna.sportoo.models;

import androidx.room.Entity;

@Entity
public class City {
    public String cityName;
    public int zipCode;
    public String country;

    public City(String cityName, int zipCode, String country) {
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.country = country;
    }
}

