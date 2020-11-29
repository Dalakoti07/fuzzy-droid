package com.dalakoti07.foodrecipeapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    private static String baseUrl= "https://s3-ap-southeast-1.amazonaws.com/he-public-data/";
    private static volatile Retrofit retrofitInstance;
    private static Apis apiClient;

    private NetworkHelper(){
        retrofitInstance = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient=retrofitInstance.create(Apis.class);
    }

    public synchronized static Apis getApiClient(){
        if(apiClient==null){
            if(retrofitInstance==null){
                retrofitInstance = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            apiClient=retrofitInstance.create(Apis.class);
            return apiClient;
        }else{
            return apiClient;
        }
    }
}
