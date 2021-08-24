package com.worna.sportoo.repositories.web.dto;

public class SportHallCustomerDTO {

    public String email;
    public Integer sport_hall;

    public SportHallCustomerDTO(String email, Integer sport_hall) {
        this.email = email;
        this.sport_hall = sport_hall;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSport_hall() {
        return sport_hall;
    }

    public void setSport_hall(Integer sport_hall) {
        this.sport_hall = sport_hall;
    }
}
