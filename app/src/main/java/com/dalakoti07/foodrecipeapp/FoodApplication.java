package com.dalakoti07.foodrecipeapp;

import android.app.Application;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;

import java.util.ArrayList;

public class FoodApplication extends Application {
    private ArrayList<FoodRecipe> cartFoodItems= new ArrayList<>();

    public void addItemsToCart(FoodRecipe foodRecipe){
        cartFoodItems.add(foodRecipe);
    }

    public ArrayList<FoodRecipe> getAllCartItems(){
        return cartFoodItems;
    }
}
