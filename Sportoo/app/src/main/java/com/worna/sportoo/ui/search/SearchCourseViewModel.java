package com.worna.sportoo.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.repositories.web.RetrofitConfigurationService;
import com.worna.sportoo.repositories.web.SportooWebService;
import com.worna.sportoo.repositories.web.dto.CustomerCourseDTO;
import com.worna.sportoo.utils.errors.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCourseViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> _isRegistred = new MutableLiveData<>(false);
    private LiveData<Boolean> isRegistred = _isRegistred;


    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;


    public SearchCourseViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();
    }



    public void isCourseCustomerExistFromWeb(String email, Integer courseId) {
        sportooWebService.isCustomerCourseExist(new CustomerCourseDTO(email, courseId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    _isRegistred.setValue(true);
                    _error.setValue(null);
                } else {
                    if(response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                        _isRegistred.setValue(false);
                        _error.setValue(null);
                    } else {
                        _isRegistred.setValue(false);
                        _error.setValue(NetworkError.REQUEST_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }

    public void addCourseCustomerToWeb(String email, Integer courseId) {
        sportooWebService.addCustomerCourse(new CustomerCourseDTO(email, courseId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    _isRegistred.setValue(true);
                    _error.setValue(null);
                } else {
                    isCourseCustomerExistFromWeb(email, courseId);
                    _error.setValue(NetworkError.REQUEST_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }

    public void deleteCourseCustomerToWeb(String email, Integer courseId) {
        sportooWebService.deleteCustomerCourse(new CustomerCourseDTO(email, courseId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    _isRegistred.setValue(false);
                    _error.setValue(null);
                } else {
                    isCourseCustomerExistFromWeb(email, courseId);
                    _error.setValue(NetworkError.REQUEST_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
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

    public LiveData<Boolean> getIsRegistred() { return isRegistred;}

}
