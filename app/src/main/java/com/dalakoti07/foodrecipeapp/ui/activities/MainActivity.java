package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dalakoti07.foodrecipeapp.FoodApplication;
import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.ui.viewmodels.MainActivityViewModel;
import com.dalakoti07.foodrecipeapp.utils.CartItemCounter;
import com.dalakoti07.foodrecipeapp.utils.TinderCard;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.List;


// todo add disk cache
public class MainActivity extends AppCompatActivity implements TinderCard.addToCartListener{
    private static final String TAG = "MainActivity";
    private ArrayList<FoodRecipe> foodRecipesList= new ArrayList<>();
    private ProgressBar progressBar;
    private SwipePlaceHolderView swipePlaceHolderView;
    private Context context;
    private CartItemCounter cartItemCounter;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipePlaceHolderView = findViewById(R.id.swipe_place_holder);
        context = getApplicationContext();
        progressBar= findViewById(R.id.progress_bar);
        cartItemCounter= new CartItemCounter(findViewById(R.id.cart_menu_option));
        mainActivityViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);
        makeApiCallAndAddObserver();
        setUpTinderSwipeListener();
        findViewById(R.id.cart_menu_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,CartActivity.class));
            }
        });
    }

    private void setUpTinderSwipeListener() {
        mainActivityViewModel.isDataFetchedFromServer().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean currentState) {
                if(currentState){
                    swipePlaceHolderView.getBuilder()
                            .setDisplayViewCount(3)
                            .setSwipeDecor(new SwipeDecor()
                                    .setPaddingTop(20)
                                    .setRelativeScale(0.01f)
                                    .setSwipeInMsgLayoutId(R.layout.food_swipe_in_msg_view)
                                    .setSwipeOutMsgLayoutId(R.layout.food_swipe_out_msg_view));

                    for(FoodRecipe food : foodRecipesList){
                        swipePlaceHolderView.addView(new TinderCard(context, food, swipePlaceHolderView,MainActivity.this));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(context, "Error: "+MainActivityViewModel.errorMessage, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void makeApiCallAndAddObserver() {
        mainActivityViewModel.fetchTheDataFromRepository().observe(this, new Observer<List<FoodRecipe>>() {
            @Override
            public void onChanged(List<FoodRecipe> foodRecipes) {
                if(foodRecipes!=null)
                    foodRecipesList.addAll(foodRecipes);
                else
                    Toast.makeText(context, "Error: "+MainActivityViewModel.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void addToCart(FoodRecipe foodRecipe) {
        //add check, add to foodApplication's arrayList
        ((FoodApplication)context).addItemsToCart(foodRecipe);
        cartItemCounter.increaseCount();
    }
}