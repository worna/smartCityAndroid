package com.worna.sportoo.repositories.web;

import com.worna.sportoo.models.Login;
import com.worna.sportoo.repositories.web.dto.CourseDto;
import com.worna.sportoo.repositories.web.dto.CustomerDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SportooWebService {

    @GET("user/{userId}")
    Call<CustomerDto> getCustomer(@Path("userId") Integer userId);

    @GET("course/{id}")
    Call<CourseDto> getCourse(@Path("id") Integer id);

    @POST("user/login")
    Call<String> login(@Body Login login);
}
