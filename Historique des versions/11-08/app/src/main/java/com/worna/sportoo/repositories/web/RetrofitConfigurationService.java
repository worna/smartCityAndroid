package com.worna.sportoo.repositories.web;

import android.content.Context;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import com.worna.sportoo.utils.ConnectivityCheckInterceptor;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitConfigurationService {
    private static final String BASE_URL = "https://sportooapi.azurewebsites.net:443/";

    private Retrofit retrofitClient;
    private static SportooWebService sportooWebService = null;

    private RetrofitConfigurationService(Context context) {
        initializeRetrofit(context);
    }

    private void initializeRetrofit(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityCheckInterceptor(context))
                .build();

        Moshi moshiConverter = new Moshi.Builder()
                .add(new KotlinJsonAdapterFactory())
                .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                .build();

        this.retrofitClient = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
                .baseUrl(BASE_URL)
                .build();
    }

    public static RetrofitConfigurationService getInstance(Context context) {
        return new RetrofitConfigurationService(context);
    }

    public SportooWebService sportooWebService() {
        if (sportooWebService == null) {
            sportooWebService = retrofitClient.create(SportooWebService.class);
        }
        return sportooWebService;
    }
}
