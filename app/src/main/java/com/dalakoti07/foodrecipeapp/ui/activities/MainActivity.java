package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;
import com.dalakoti07.foodrecipeapp.network.NetworkHelper;
import com.dalakoti07.foodrecipeapp.utils.TinderCard;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<FoodRecipe> foodRecipesList= new ArrayList<>();
    private ProgressBar progressBar;
    private SwipePlaceHolderView swipePlaceHolderView;
    private Context context;
    private MutableLiveData<Boolean> fetchedTheDataFromServer= new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipePlaceHolderView = (SwipePlaceHolderView)findViewById(R.id.swipe_place_holder);
        context = getApplicationContext();
        progressBar= findViewById(R.id.progress_bar);
        makeApiCall();
        setUpTinderSwipeListener();
    }

    private void setUpTinderSwipeListener() {
        fetchedTheDataFromServer.observe(this, new Observer<Boolean>() {
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
                        swipePlaceHolderView.addView(new TinderCard(context, food, swipePlaceHolderView));
                    }
                }
            }
        });
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
                        Log.d(TAG, "onResponse success: "+foodRecipesList.get(0));
                        fetchedTheDataFromServer.setValue(true);
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