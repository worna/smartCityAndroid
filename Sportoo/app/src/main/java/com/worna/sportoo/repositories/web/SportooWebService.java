package com.worna.sportoo.repositories.web;

import com.worna.sportoo.models.Login;
import com.worna.sportoo.repositories.web.dto.CourseDto;
import com.worna.sportoo.repositories.web.dto.CustomerCourseDTO;
import com.worna.sportoo.repositories.web.dto.CustomerDto;
import com.worna.sportoo.repositories.web.dto.SportHallCustomerDTO;
import com.worna.sportoo.repositories.web.dto.SportHallDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SportooWebService {

    @POST("customer/")
    Call<ResponseBody> registration(@Body CustomerDto customerDto);

    @GET("customer/{id}")
    Call<CustomerDto> getCustomer(@Path("id") String id);

    @GET("customerCourse/customer/next/{email}")
    Call<List<CourseDto>> getNextCustomerCourses(@Path("email") String email);

    @POST("/sportHallCustomer/exist/{email}-{course}")
    Call<ResponseBody> isRegisteredForCourse(@Path("email") String email, @Path("course") String course);

    @GET("course")
    Call<List<CourseDto>> getCourses();

    @GET("course/{id}")
    Call<CourseDto> getCourse(@Path("id") Integer id);

    @GET("/course/sportHall/{id}")
    Call<List<CourseDto>> getSportHallCourse(@Path("id") Integer id);

    @POST("/customerCourse/exist")
    Call<ResponseBody> isCustomerCourseExist(@Body CustomerCourseDTO customerCourse);

    @POST("/customerCourse")
    Call<ResponseBody> addCustomerCourse(@Body CustomerCourseDTO customerCourse);

    @HTTP(method = "DELETE", path = "/customerCourse", hasBody = true)
    Call<ResponseBody> deleteCustomerCourse(@Body CustomerCourseDTO customerCourse);

    @POST("user/login")
    Call<String> login(@Body Login login);

    @POST("/sportHallCustomer/exist")
    Call<String> isSportHallCustomerExist(@Body SportHallCustomerDTO sportHallCustomer);

    @POST("/sportHallCustomer")
    Call<ResponseBody> addSportHallCustomer(@Body SportHallCustomerDTO sportHallCustomer);

    @HTTP(method = "DELETE", path = "/sportHallCustomer", hasBody = true)
    Call<ResponseBody> deleteSportHallCustomer(@Body SportHallCustomerDTO sportHallCustomer);

    @GET("sportHall/{id}")
    Call<SportHallDto> getSportHall(@Path("id") Integer id);

    @GET("sportHall")
    Call<List<SportHallDto>> getSportHalls();

}
