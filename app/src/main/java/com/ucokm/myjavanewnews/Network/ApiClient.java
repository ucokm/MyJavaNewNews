package com.ucokm.myjavanewnews.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://newsapi.org/v2";
    private static Retrofit.Builder builderSLL =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory);

}
