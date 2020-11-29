package com.dalakoti07.foodrecipeapp;

import android.app.Application;
import android.content.Context;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;

import java.util.ArrayList;

public class FoodApplication extends Application {
    private ArrayList<FoodRecipe> cartFoodItems= new ArrayList<>();
    private static FoodApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    /*public void addItemsToCart(FoodRecipe foodRecipe){
        cartFoodItems.add(foodRecipe);
    }

    public ArrayList<FoodRecipe> getAllCartItems(){
        return cartFoodItems;
    }*/

    public static Context getAppContext(){
        return context;
    }
}
