package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.dalakoti07.foodrecipeapp.FoodApplication;
import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.repository.FoodRepository;
import com.dalakoti07.foodrecipeapp.room.FoodDatabaseModel;
import com.dalakoti07.foodrecipeapp.room.RecipeDatabase;
import com.dalakoti07.foodrecipeapp.utils.DataTransformer;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

// todo cart items are not appearing at first go but at second go
public class CartActivity extends AppCompatActivity {

    //todo handle consistant favs even when data is fetched from server, may be need to make customized onconflict resolver
    private static final String TAG = "CartActivity";
    private RecyclerView rv_foods;
    private ArrayList<FoodRecipe> foodsList= new ArrayList<>();
    private FoodRepository foodRepository=FoodRepository.getInstance(RecipeDatabase.getRecipeDatabase(getApplication()));
    private LiveData<List<FoodDatabaseModel>> favsList=foodRepository.getFavouriteFoods();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv_foods=findViewById(R.id.rv_foods);
        progressBar=findViewById(R.id.progress_bar);
        //foodsList=((FoodApplication)getApplicationContext()).getAllCartItems();
        rv_foods.setAdapter(new FoodRecyclerViewAdapter(foodsList,this));
        favsList.observe(this, foodDatabaseModels -> {
            if(foodDatabaseModels!=null && foodDatabaseModels.size()>0){
                foodsList.clear();
                Toasty.success(CartActivity.this,"Fetched Favs").show();
                Log.d(TAG, "got the favs size: "+foodDatabaseModels.size());
                foodsList.addAll(DataTransformer.databaseListToNetworkList((ArrayList<FoodDatabaseModel>) foodDatabaseModels));
                rv_foods.getAdapter().notifyDataSetChanged();
            }else
                Toasty.error(CartActivity.this,"Empty List").show();
            progressBar.setVisibility(View.INVISIBLE);
        });
    }
}