package com.dalakoti07.foodrecipeapp.utils;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.room.FoodDatabaseModel;

import java.util.ArrayList;

public class DataTransformer {

    public static ArrayList<FoodDatabaseModel> modelListToDatabaseModelList(ArrayList<FoodRecipe> modelList){
        ArrayList<FoodDatabaseModel> databaseModelArrayList= new ArrayList<>();
        for(FoodRecipe foodRecipe:modelList){
            databaseModelArrayList.add(new FoodDatabaseModel(foodRecipe.getId(),foodRecipe.getName(),
                    foodRecipe.getImage(),foodRecipe.getCategory(),foodRecipe.getLabel(),foodRecipe.getPrice(),foodRecipe.getDescription()));
        }
        return databaseModelArrayList;
    }

    public static ArrayList<FoodRecipe> databaseListToNetworkList(ArrayList<FoodDatabaseModel> databaseModelsArrayList){
        ArrayList<FoodRecipe> foodRecipesArrayList= new ArrayList<>();
        for(FoodDatabaseModel foodDatabaseModel: databaseModelsArrayList){
            foodRecipesArrayList.add(new FoodRecipe(foodDatabaseModel.getId(),foodDatabaseModel.getName(),
                    foodDatabaseModel.getImage(),foodDatabaseModel.getCategory(),foodDatabaseModel.getLabel(),foodDatabaseModel.getPrice(),foodDatabaseModel.getDescription()));
        }
        return foodRecipesArrayList;
    }

}
