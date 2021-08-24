package com.worna.sportoo.repositories.web.dto;

import com.squareup.moshi.Json;

public class SportHallDto {
    @Json(name = "id_sport_hall") // JSON key
    private Integer id_sport_hall;
    private String name;
    private CustomerDto manager;
    private String phone_number;
    private String email;
    private String city_name;
    private Integer zip_code;
    private String country;
    private String address;

    public Integer getId() {
        return id_sport_hall;
    }

    public void setId(Integer id) {
        this.id_sport_hall = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerDto getManager() {
        return manager;
    }

    public void setManager(CustomerDto manager) {
        this.manager = manager;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String cityName) {
        this.city_name = cityName;
    }

    public Integer getZip_code() {
        return zip_code;
    }

    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SportHallDto{" +
                "id=" + id_sport_hall +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                ", phoneNumber='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", cityName='" + city_name + '\'' +
                ", zipCode=" + zip_code +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
