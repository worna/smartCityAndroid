package com.worna.sportoo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity (foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "manager"))
public class SportHall {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    @ColumnInfo(name = "manager")
    public int manager;
    public String phoneNumber;
    public String email;
    public String address;
    public String cityName;
    public int zipCode;
    public String country;

    public SportHall(String name, int manager, String phoneNumber, String email, String address, String cityName, int zipCode, String country) {
        this.name = name;
        this.manager = manager;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.country = country;
    }
}

