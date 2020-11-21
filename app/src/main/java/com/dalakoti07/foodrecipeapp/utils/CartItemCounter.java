package com.dalakoti07.foodrecipeapp.utils;

import android.view.View;
import android.widget.TextView;

import com.dalakoti07.foodrecipeapp.R;

public class CartItemCounter {
    private TextView tv_cart_item_count;
    private int count=0;

    public CartItemCounter(View view){
        tv_cart_item_count= view.findViewById(R.id.tv_cart_count);
    }

    public void increaseCount(){
        count++;
        tv_cart_item_count.setText(count+"");
    }
}
