package com.worna.sportoo.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.models.SportHall;
import com.worna.sportoo.repositories.web.RetrofitConfigurationService;
import com.worna.sportoo.repositories.web.SportooWebService;
import com.worna.sportoo.repositories.web.dto.SportHallDto;
import com.worna.sportoo.services.mappers.SportHallMapper;
import com.worna.sportoo.utils.errors.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends AndroidViewModel {
    private MutableLiveData<List<SportHall>> _sportHall = new MutableLiveData<>();
    private LiveData<List<SportHall>> sportHall = _sportHall;

    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;
    private SportHallMapper sportHallMapper;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();
        this.sportHallMapper = SportHallMapper.getInstance();
    }

    public void getSportHallFromWeb() {
        sportooWebService.getSportHalls().enqueue(new Callback<List<SportHallDto>>() {
            @Override
            public void onResponse(@NotNull Call<List<SportHallDto>> call, @NotNull Response<List<SportHallDto>> response) {
                if (response.isSuccessful()) {
                    List<SportHall> newList = new ArrayList<>();
                    response.body().forEach((sportHall -> newList.add(sportHallMapper.mapToSportHall(sportHall))));
                    _sportHall.setValue(newList);
                    _error.setValue(null);
                } else {
                    _error.setValue(NetworkError.REQUEST_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<SportHallDto>> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }

    public LiveData<List<SportHall>> getSportHall() {
        return sportHall;
    }

    public LiveData<NetworkError> getError() {
        return error;
    }

}
