package com.worna.sportoo.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worna.sportoo.models.Course;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.repositories.web.RetrofitConfigurationService;
import com.worna.sportoo.repositories.web.SportooWebService;
import com.worna.sportoo.repositories.web.dto.CourseDto;
import com.worna.sportoo.services.mappers.CourseMapper;
import com.worna.sportoo.utils.errors.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCoursesViewModel extends AndroidViewModel {


    private MutableLiveData<List<Course>> _courses = new MutableLiveData<>();
    private LiveData<List<Course>> courses = _courses;

    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;

    private CourseMapper courseMapper;

    public SearchCoursesViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();
        this.courseMapper = CourseMapper.getInstance();
    }

    public void getSportHallCourseFromWeb(Integer id_sport_hall) {
        sportooWebService.getSportHallCourse(id_sport_hall).enqueue(new Callback<List<CourseDto>>() {
            @Override
            public void onResponse(@NotNull Call<List<CourseDto>> call, @NotNull Response<List<CourseDto>> response) {
                if (response.isSuccessful()) {
                    List<Course> newList = new ArrayList<>();
                    response.body().forEach((course -> newList.add(courseMapper.mapToCourse(course))));
                    _courses.setValue(newList);
                    _error.setValue(null);
                } else {
                    _error.setValue(NetworkError.REQUEST_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CourseDto>> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }


    public LiveData<NetworkError> getError() {
        return error;
    }


    public LiveData<List<Course>> getCourses() { return courses;}

}
