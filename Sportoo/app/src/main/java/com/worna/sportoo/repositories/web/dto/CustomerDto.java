package com.worna.sportoo.repositories.web.dto;

import com.squareup.moshi.Json;

import java.util.Date;

public class CustomerDto {
    @Json(name = "id") // JSON key
    private Integer id;

    private String first_name;
    private String last_name;
    private Date birth_date;
    private Integer gender;
    private String phone_number;
    private String email;
    private String password;
    private Date inscription_date;
    private Integer is_manager;
    private Integer is_instructor;
    private String language;
    private String address;
    private String city_name;
    private Integer zip_code;
    private String country;

    public CustomerDto(Integer id, String first_name, String last_name, Date birth_date, Integer gender, String phone_number, String email, String password, Date inscription_date, Integer is_manager, Integer is_instructor, String language, String address, String city_name, Integer zip_code, String country) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.inscription_date = inscription_date;
        this.is_manager = is_manager;
        this.is_instructor = is_instructor;
        this.language = language;
        this.address = address;
        this.city_name = city_name;
        this.zip_code = zip_code;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInscription_date() {
        return inscription_date;
    }

    public void setInscription_date(Date inscription_date) {
        this.inscription_date = inscription_date;
    }

    public Integer getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(Integer is_manager) {
        this.is_manager = is_manager;
    }

    public Integer getIs_instructor() {
        return is_instructor;
    }

    public void setIs_instructor(Integer is_instructor) {
        this.is_instructor = is_instructor;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birth_date=" + birth_date +
                ", gender=" + gender +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", inscription_date=" + inscription_date +
                ", is_manager=" + is_manager +
                ", is_instructor=" + is_instructor +
                ", language='" + language + '\'' +
                ", address='" + address + '\'' +
                ", city_name='" + city_name + '\'' +
                ", zip_code=" + zip_code +
                ", country='" + country + '\'' +
                '}';
    }
}
