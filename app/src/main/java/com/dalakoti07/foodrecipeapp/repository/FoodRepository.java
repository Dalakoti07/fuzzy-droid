package com.dalakoti07.foodrecipeapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.network.NetworkHelper;
import com.dalakoti07.foodrecipeapp.network.NoConnectivityException;
import com.dalakoti07.foodrecipeapp.room.FoodDatabaseModel;
import com.dalakoti07.foodrecipeapp.room.RecipeDatabase;
import com.dalakoti07.foodrecipeapp.utils.DataTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    private static final String TAG = "FoodRepository";
    private RecipeDatabase recipeDatabase;
    private static FoodRepository foodRepositoryInstance;
    private MutableLiveData<String> networkErrorString= new MutableLiveData<>();
    LiveData<List<FoodDatabaseModel>> foodList= new MutableLiveData<>();

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

    public LiveData<List<FoodDatabaseModel>> fetchTheDataFromServer(){
        //todo fix this expose livedata not mutable live data
        foodList= recipeDatabase.foodDao().getAllFoods();
        final Call<List<FoodRecipe>> foodRecipeCall = NetworkHelper.getApiClient().getRecipe();
        foodRecipeCall.enqueue(new Callback<List<FoodRecipe>>() {
            @Override
            public void onResponse(Call<List< FoodRecipe >> call, Response<List<FoodRecipe>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        // doing database write in background thread
                        recipeDatabase.databaseWriteExecutor.execute(()->{
                            ArrayList<FoodDatabaseModel> databaseModelArrayList= DataTransformer.modelListToDatabaseModelList((ArrayList<FoodRecipe>) response.body());
                            for(FoodDatabaseModel foodDatabaseModel:databaseModelArrayList){
                                recipeDatabase.foodDao().insert(foodDatabaseModel);
                            }
                            Log.d(TAG, "fetchTheDataFromRepository: fetched via api call and saved "+" instances ");
                            networkErrorString.postValue("Success");
                        });
                    }
                }else{
                    networkErrorString.setValue(""+response.code()+" "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<FoodRecipe>> call, Throwable t) {
                if(t instanceof NoConnectivityException) {
                    //cache the data from local db
                    networkErrorString.setValue("No Connectivity");
                    foodList=recipeDatabase.foodDao().getAllFoods();
                    Log.d(TAG, "no internet connectivity status list: "+foodList+ " value"+foodList.getValue());
                    if(foodList.getValue()!=null && foodList.getValue().size()>0)
                        Log.d(TAG, "no internet connectivity "+foodList.getValue().get(0));
                }else{
                    Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    networkErrorString.setValue(""+t.getLocalizedMessage());
                }
            }
        });
        return foodList;
    }
}
