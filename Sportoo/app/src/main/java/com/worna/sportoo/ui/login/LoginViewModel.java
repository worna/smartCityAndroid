package com.worna.sportoo.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worna.sportoo.models.Login;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.repositories.web.RetrofitConfigurationService;
import com.worna.sportoo.repositories.web.SportooWebService;
import com.worna.sportoo.utils.errors.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> _token = new MutableLiveData<>();
    private LiveData<String> token = _token;

    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();

    }

    public void getTokenFromWeb(String email, String password) {
        Login login = new Login(email, password);
        sportooWebService.login(login).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()) {
                    _token.setValue(response.body());
                    _error.setValue(null);
                } else {
                    if (response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                        _error.setValue(NetworkError.NOT_FOUND);
                    } else {
                        _error.setValue(NetworkError.REQUEST_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }

    public LiveData<String> getToken() {
        return token;
    }

    public LiveData<NetworkError> getError() {
        return error;
    }
}
