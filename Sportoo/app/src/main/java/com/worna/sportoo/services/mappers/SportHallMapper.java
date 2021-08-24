package com.worna.sportoo.services.mappers;

import com.worna.sportoo.models.SportHall;
import com.worna.sportoo.repositories.web.dto.SportHallDto;

public class SportHallMapper {
    private static SportHallMapper instance = null;

    public static SportHallMapper getInstance() {
        if (instance == null) {
            instance = new SportHallMapper();
        }
        return instance;
    }

    public SportHall mapToSportHall(SportHallDto dto) {
        if (dto == null) {
            return null;
        }

        return new SportHall(dto.getId(), dto.getName(), CustomerMapper.getInstance().mapToCustomer(dto.getManager()), dto.getPhone_number(), dto.getEmail(), dto.getAddress(), dto.getCity_name(), dto.getZip_code(), dto.getCountry());
    }
}
