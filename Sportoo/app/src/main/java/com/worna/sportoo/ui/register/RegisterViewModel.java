package com.worna.sportoo.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worna.sportoo.models.Customer;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.repositories.web.RetrofitConfigurationService;
import com.worna.sportoo.repositories.web.SportooWebService;
import com.worna.sportoo.repositories.web.dto.CustomerDto;
import com.worna.sportoo.services.mappers.CustomerMapper;
import com.worna.sportoo.utils.errors.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterViewModel extends AndroidViewModel {


    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;
    private CustomerMapper customerMapper;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();
        this.customerMapper = CustomerMapper.getInstance();
    }

    public void registrationToWeb(Customer customer) {
        CustomerDto dto = customerMapper.mapToCustomerDto(customer);
        sportooWebService.registration(dto).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    _error.setValue(null);
                } else {
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




}
