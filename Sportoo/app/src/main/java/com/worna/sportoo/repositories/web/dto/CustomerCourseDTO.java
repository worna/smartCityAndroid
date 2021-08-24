package com.worna.sportoo.repositories.web.dto;

public class CustomerCourseDTO {

    public String email_customer;
    public Integer id_course;

    public CustomerCourseDTO(String email, Integer course) {
        this.email_customer = email;
        this.id_course = course;
    }

    public String getEmail() {
        return email_customer;
    }

    public void setEmail(String email) {
        this.email_customer = email;
    }

    public Integer getSport_hall() {
        return id_course;
    }

    public void setSport_hall(Integer sport_hall) {
        this.id_course = sport_hall;
    }
}
