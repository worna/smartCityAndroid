package com.worna.sportoo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity (foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "manager"))
public class SportHall {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public String name;
    @ColumnInfo(name = "manager")
    public Customer manager;
    public String phoneNumber;
    public String email;
    public String address;
    public String cityName;
    public Integer zipCode;
    public String country;

    public SportHall(Integer id, String name, Customer manager, String phoneNumber, String email, String address, String cityName, Integer zipCode, String country) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getCompletAddress() {
        return address +
                "\n" +
                zipCode +
                " - " +
                cityName +
                " (" +
                country.substring(0,2).toUpperCase() +
                ")";
    }
}

