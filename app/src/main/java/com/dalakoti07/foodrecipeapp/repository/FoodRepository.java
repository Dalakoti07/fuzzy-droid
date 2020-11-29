package com.dalakoti07.foodrecipeapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.network.NetworkHelper;
import com.dalakoti07.foodrecipeapp.room.RecipeDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    private static final String TAG = "FoodRepository";
    private volatile RecipeDatabase recipeDatabase;
    private static FoodRepository foodRepositoryInstance;
    private MutableLiveData<String> networkErrorString= new MutableLiveData<>();

    private FoodRepository(RecipeDatabase recipeDatabase){
        Log.d(TAG, "FoodRepository: created a repository instance");
        this.recipeDatabase=recipeDatabase;
    }

    public MutableLiveData<String> returnNetworkStatus(){
        return networkErrorString;
    }

    public static synchronized FoodRepository getInstance(RecipeDatabase recipeDatabase){
        if(foodRepositoryInstance==null){
            foodRepositoryInstance= new FoodRepository(recipeDatabase);
        }
        return foodRepositoryInstance;
    }

    public MutableLiveData<List<FoodRecipe>> fetchTheDataFromServer(){
        MutableLiveData<List<FoodRecipe>> foodList= new MutableLiveData<>();
        Log.d(TAG, "fetchTheDataFromRepository: making a api call");
        final Call<List<FoodRecipe>> foodRecipeCall = NetworkHelper.getApiClient().getRecipe();
        foodRecipeCall.enqueue(new Callback<List<FoodRecipe>>() {
            @Override
            public void onResponse(Call<List< FoodRecipe >> call, Response<List<FoodRecipe>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        foodList.setValue(response.body());
                        networkErrorString.setValue("Success");
                    }
                }else{
                    networkErrorString.setValue(""+response.code()+" "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<FoodRecipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                //errorMessage=t.getMessage();
                networkErrorString.setValue(""+t.getLocalizedMessage());
            }
        });
        return foodList;
    }
}
