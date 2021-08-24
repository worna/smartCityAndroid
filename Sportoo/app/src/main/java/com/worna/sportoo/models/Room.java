package com.worna.sportoo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity (foreignKeys = @ForeignKey(entity = SportHall.class, parentColumns = "id", childColumns = "id_sport_hall"),
        primaryKeys = {"idRoom", "idSportHall"})
public class Room {

    public Integer idRoom;
    @ColumnInfo(name = "id_sport_hall")
    public SportHall idSportHall;
    public Integer maxCapacity;

    public Room(Integer idRoom, SportHall idSportHall, Integer maxCapacity) {
        this.idRoom = idRoom;
        this.idSportHall = idSportHall;
        this.maxCapacity = maxCapacity;
    }
}

