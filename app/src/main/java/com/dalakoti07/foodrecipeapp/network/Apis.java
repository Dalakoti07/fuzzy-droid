package com.dalakoti07.foodrecipeapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Apis {
    @GET("reciped9d7b8c.json")
    Call<List<FoodRecipe>> getRecipe();
}
