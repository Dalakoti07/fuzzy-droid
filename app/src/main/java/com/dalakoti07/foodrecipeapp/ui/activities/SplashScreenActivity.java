package com.dalakoti07.foodrecipeapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dalakoti07.foodrecipeapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    private Handler splashHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashHandler= new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }
        },1000);
    }
}