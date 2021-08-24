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
    public SportHall sportHall;
    @ColumnInfo(name = "id_room")
    public Room room;
    public Date startingDateTime;
    public Date endingDateTime;
    public String level;
    public String activity;
    @ColumnInfo(name = "id_instructor")
    public Customer instructor;

    public Course(int id, SportHall sportHall, Room room, Date startingDateTime, Date endingDateTime, String level, String activity, Customer instructor) {
        this.id = id;
        this.sportHall = sportHall;
        this.room = room;
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.level = level;
        this.activity = activity;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", sportHall=" + sportHall +
                ", room=" + room +
                ", startingDateTime=" + startingDateTime +
                ", endingDateTime=" + endingDateTime +
                ", level='" + level + '\'' +
                ", activity='" + activity + '\'' +
                ", instructor=" + instructor +
                '}';
    }
}

