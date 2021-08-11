package com.worna.sportoo.services.mappers;

import com.worna.sportoo.models.Course;
import com.worna.sportoo.repositories.web.dto.CourseDto;

public class CourseMapper {
    private static CourseMapper instance = null;

    public static CourseMapper getInstance() {
        if (instance == null) {
            instance = new CourseMapper();
        }
        return instance;
    }

    public Course mapToCourse(CourseDto dto) {
        if (dto == null) {
            return null;
        }

        return new Course(dto.getSportHall(),dto.getStartingDateTime(), dto.getEndingDateTime(), dto.getLevel(), dto.getActivity(), dto.getRoom(), dto.getIdInstructor());
    }
}
