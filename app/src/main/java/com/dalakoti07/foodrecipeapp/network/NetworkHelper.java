package com.dalakoti07.foodrecipeapp.network;

import android.content.Context;
import android.util.Log;

import com.dalakoti07.foodrecipeapp.FoodApplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    private static final String TAG = "NetworkHelper";
    private static String baseUrl= "https://s3-ap-southeast-1.amazonaws.com/he-public-data/";
    private static volatile Retrofit retrofitInstance;
    private static Apis apiClient;

    private NetworkHelper(){
        // empty
    }

    public synchronized static Apis getApiClient(){
        if(apiClient==null){
            if(retrofitInstance==null){
                OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new NetworkConnectionInterceptor(FoodApplication.getAppContext()));
                // Adding NetworkConnectionInterceptor with okHttpClientBuilder.

                Log.d(TAG, "creating REST client ");
                retrofitInstance = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(oktHttpClient.build())
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
