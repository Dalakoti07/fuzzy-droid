package com.dalakoti07.foodrecipeapp.ui.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.repository.FoodRepository;
import com.dalakoti07.foodrecipeapp.room.RecipeDatabase;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<List<FoodRecipe>> foodList= new MutableLiveData<>();
    private MutableLiveData<String> networkResponse= new MutableLiveData<>();
    private FoodRepository foodRepository=FoodRepository.getInstance(RecipeDatabase.getRecipeDatabase(getApplication()));

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> returnNetworkStatus(){
        return foodRepository.returnNetworkStatus();
    }

    // todo handle the network error gracefully
    public MutableLiveData<List<FoodRecipe>> fetchTheDataFromRepository(){
        if(networkResponse.getValue()!=null && networkResponse.getValue().equals("Success"))
            return foodList;
        Log.d(TAG, "fetchTheDataFromRepository: making a api call");
        return foodRepository.fetchTheDataFromServer();
    }
}
