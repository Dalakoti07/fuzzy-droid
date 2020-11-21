package com.dalakoti07.foodrecipeapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.food_card_view)
public class TinderCard {
    private static final String TAG = "TinderCardTag";

    @View(R.id.iv_food_image)
    private ImageView foodImageView;

    @View(R.id.tv_food_name)
    private TextView tv_food_name;

    @View(R.id.tv_food_category)
    private TextView tv_food_category;

    @View(R.id.tv_food_price)
    private TextView tv_food_price;

    private FoodRecipe foodRecipe;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, FoodRecipe profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        foodRecipe = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(foodRecipe.getImage()).into(foodImageView);
        tv_food_name.setText(foodRecipe.getName());
        tv_food_category.setText(foodRecipe.getCategory());
        tv_food_price.setText(foodRecipe.getPrice());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d(TAG, "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d(TAG, "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d(TAG, "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d(TAG, "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d(TAG, "onSwipeOutState");
    }
}
