package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<FoodRecipe> foodRecipesList= new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= findViewById(R.id.progress_bar);
        makeApiCall();
    }

    // todo put it in viewmodel
    private void makeApiCall() {
        final Call<List<FoodRecipe>> foodRecipeCall =NetworkHelper.getApiClient().getRecipe();
        foodRecipeCall.enqueue(new Callback<List<FoodRecipe>>() {
            @Override
            public void onResponse(Call<List<FoodRecipe>> call, Response<List<FoodRecipe>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Fetched",Toast.LENGTH_SHORT).show();
                    if(response.body()!=null){
                        foodRecipesList.addAll(response.body());
                        Log.d(TAG, "onResponse: "+foodRecipesList.get(0));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<FoodRecipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }


}