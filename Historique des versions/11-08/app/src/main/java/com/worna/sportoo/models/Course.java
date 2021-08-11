package com.worna.sportoo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;


/**
 * Each entity is materialized by a class (1 entity = 1 SQL table)
 */
@Entity (foreignKeys = {
            @ForeignKey(entity = Room.class, parentColumns = {"idSportHall", "idRoom"}, childColumns = {"id_sport_hall", "id_room"}),
            @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "id_instructor")})
public class Course {

    public int id;
    @ColumnInfo(name = "id_sport_hall")
    public int idSportHall;
    @ColumnInfo(name = "id_room")
    public int room;
    public Date startingDateTime;
    public Date endingDateTime;
    public String level;
    public String activity;
    @ColumnInfo(name = "id_instructor")
    public int idInstructor;

    public Course(int idSportHall, Date startingDateTime, Date endingDateTime, String level, String activity, int room, int idInstructor) {
        this.idSportHall = idSportHall;
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.level = level;
        this.activity = activity;
        this.room = room;
        this.idInstructor = idInstructor;
    }
}

