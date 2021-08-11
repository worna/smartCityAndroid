package com.worna.sportoo.repositories.web.dto;

import com.squareup.moshi.Json;

import java.util.Date;

public class CourseDto {
    @Json(name = "id") // JSON key
    private Integer id;
    public int sportHall;
    public int room;
    public Date startingDateTime;
    public Date endingDateTime;
    public String level;
    public String activity;
    public int idInstructor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSportHall() {
        return sportHall;
    }

    public void setSportHall(int sportHall) {
        this.sportHall = sportHall;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Date getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(Date startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public Date getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(Date endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(int idInstructor) {
        this.idInstructor = idInstructor;
    }
}
