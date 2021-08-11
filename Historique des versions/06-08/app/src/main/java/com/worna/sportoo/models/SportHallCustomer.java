package com.worna.sportoo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity (foreignKeys = {
            @ForeignKey(entity = SportHall.class, parentColumns = "id", childColumns = "id_sport_hall"),
            @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "id_customer")},
        primaryKeys = {"idSportHall", "idCustomer"})
public class SportHallCustomer {
    @ColumnInfo(name = "id_sport_hall")
    public int idSportHall;
    @ColumnInfo(name = "id_customer")
    public int idCustomer;

    public SportHallCustomer(int idSportHall, int idCustomer) {
        this.idSportHall = idSportHall;
        this.idCustomer = idCustomer;
    }
}

