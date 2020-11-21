package com.dalakoti07.foodrecipeapp.ui.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dalakoti07.foodrecipeapp.R;
import com.dalakoti07.foodrecipeapp.network.FoodRecipe;

import java.util.ArrayList;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.FoodViewHolder> {
    private ArrayList<FoodRecipe> foodRecipesList;
    private Context context;

    public FoodRecyclerViewAdapter(ArrayList<FoodRecipe> list,Context context){
        this.foodRecipesList=list;
        this.context=context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item_food,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(foodRecipesList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodRecipesList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_food_name,tv_food_price,tv_food_category;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.iv_food_image);
            tv_food_name=itemView.findViewById(R.id.tv_food_name);
            tv_food_category=itemView.findViewById(R.id.tv_food_category);
            tv_food_price=itemView.findViewById(R.id.tv_food_price);
        }

        public void bind(FoodRecipe foodRecipe){
            Glide.with(context).load(foodRecipe.getImage()).centerCrop().into(imageView);
            tv_food_name.setText(foodRecipe.getName());
            tv_food_category.setText(foodRecipe.getCategory());
            tv_food_price.setText(foodRecipe.getPrice());
        }
    }
}
