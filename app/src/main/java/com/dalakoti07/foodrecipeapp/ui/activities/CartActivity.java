package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dalakoti07.foodrecipeapp.FoodApplication;
import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;

import java.util.ArrayList;

// todo add sqlite support
public class CartActivity extends AppCompatActivity {
    private RecyclerView rv_foods;
    private ArrayList<FoodRecipe> foodsList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv_foods=findViewById(R.id.rv_foods);
        foodsList=((FoodApplication)getApplicationContext()).getAllCartItems();
        rv_foods.setAdapter(new FoodRecyclerViewAdapter(foodsList,this));
    }
}