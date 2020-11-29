package com.dalakoti07.foodrecipeapp.ui.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.network.NetworkHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<Boolean> fetchedTheDataFromServer= new MutableLiveData<>(false);
    public static String errorMessage="";

    public MutableLiveData<Boolean> isDataFetchedFromServer(){
        return fetchedTheDataFromServer;
    }

    private MutableLiveData<List<FoodRecipe>> foodList= new MutableLiveData<>();

    // todo handle the network error gracefully
    public MutableLiveData<List<FoodRecipe>> fetchTheDataFromRepository(){
        if(fetchedTheDataFromServer.getValue())
            return foodList;
        Log.d(TAG, "fetchTheDataFromRepository: making a api call");
        final Call<List<FoodRecipe>> foodRecipeCall = NetworkHelper.getApiClient().getRecipe();
        foodRecipeCall.enqueue(new Callback<List<FoodRecipe>>() {
            @Override
            public void onResponse(Call<List< FoodRecipe >> call, Response<List<FoodRecipe>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        foodList.setValue(response.body());
                        fetchedTheDataFromServer.setValue(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodRecipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                errorMessage=t.getMessage();
                fetchedTheDataFromServer.setValue(false);
            }
        });
        return foodList;
    }
}
