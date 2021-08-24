package com.worna.sportoo.ui.profile;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {
    private MutableLiveData<Customer> _account = new MutableLiveData<>();
    private LiveData<Customer> account = _account;

    private MutableLiveData<NetworkError> _error = new MutableLiveData<>();
    private LiveData<NetworkError> error = _error;

    private SportooWebService sportooWebService;
    private CustomerMapper customerMapper;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.sportooWebService = RetrofitConfigurationService.getInstance(application).sportooWebService();
        this.customerMapper = CustomerMapper.getInstance();
    }

    public void getCustomerFromWeb(String email) {
        sportooWebService.getCustomer(email).enqueue(new Callback<CustomerDto>() {
            @Override
            public void onResponse(@NotNull Call<CustomerDto> call, @NotNull Response<CustomerDto> response) {
                if (response.isSuccessful()) {
                    _account.setValue(customerMapper.mapToCustomer(response.body()));
                    _error.setValue(null);
                } else {
                    _error.setValue(NetworkError.REQUEST_ERROR);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CustomerDto> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    _error.setValue(NetworkError.NO_CONNECTION);
                } else {
                    _error.setValue(NetworkError.TECHNICAL_ERROR);
                }

            }
        });
    }

    public LiveData<Customer> getAccount() {
        return account;
    }

    public LiveData<NetworkError> getError() {
        return error;
    }
}
