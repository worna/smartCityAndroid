package com.worna.sportoo.repositories.web.dto;

import com.squareup.moshi.Json;

import java.util.Date;

public class CourseDto {
    @Json(name = "id") // JSON key
    private Integer id;
    public SportHallDto sportHall;
    public RoomDto room;
    public Date starting_date_time;
    public Date ending_date_time;
    public String level;
    public String activity;
    public CustomerDto instructor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SportHallDto getSportHall() {
        return sportHall;
    }

    public void setSportHall(SportHallDto sportHall) {
        this.sportHall = sportHall;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public Date getStarting_date_time() {
        return starting_date_time;
    }

    public void setStarting_date_time(Date starting_date_time) {
        this.starting_date_time = starting_date_time;
    }

    public Date getEnding_date_time() {
        return ending_date_time;
    }

    public void setEnding_date_time(Date ending_date_time) {
        this.ending_date_time = ending_date_time;
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

    public CustomerDto getInstructor() {
        return instructor;
    }

    public void setInstructor(CustomerDto instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", sportHall=" + sportHall.getId().toString() +
                ", room=" + room.getId_room().toString() +
                ", startingDateTime=" + starting_date_time +
                ", endingDateTime=" + ending_date_time +
                ", level='" + level + '\'' +
                ", activity='" + activity + '\'' +
                ", instructor=" + instructor +
                '}';
    }
}
