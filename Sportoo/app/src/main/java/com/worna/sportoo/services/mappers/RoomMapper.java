package com.worna.sportoo.services.mappers;

import com.worna.sportoo.models.Room;
import com.worna.sportoo.repositories.web.dto.RoomDto;

public class RoomMapper {
    private static RoomMapper instance = null;

    public static RoomMapper getInstance() {
        if (instance == null) {
            instance = new RoomMapper();
        }
        return instance;
    }

    public Room mapToRoom(RoomDto dto) {
        if (dto == null) {
            return null;
        }

        return new Room(dto.getId_room(), SportHallMapper.getInstance().mapToSportHall(dto.getSportHall()), dto.getMax_capacity());
    }
}
