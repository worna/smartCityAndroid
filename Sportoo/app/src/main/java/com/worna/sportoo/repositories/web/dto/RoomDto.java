package com.worna.sportoo.repositories.web.dto;

import com.squareup.moshi.Json;

public class RoomDto {
    @Json(name = "id_room") // JSON key
    public Integer id_room;
    public SportHallDto sportHall;
    public Integer max_capacity;

    public Integer getId_room() {
        return id_room;
    }

    public void setId_room(Integer id_room) {
        this.id_room = id_room;
    }

    public SportHallDto getSportHall() {
        return sportHall;
    }

    public void setSportHall(SportHallDto sportHall) {
        this.sportHall = sportHall;
    }

    public Integer getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(Integer max_capacity) {
        this.max_capacity = max_capacity;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id_room=" + id_room +
                ", sportHall=" + sportHall +
                ", max_capacity=" + max_capacity +
                '}';
    }
}
