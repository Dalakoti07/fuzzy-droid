package com.dalakoti07.foodrecipeapp.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    public MainViewModelFactory(Application application){
        this.application=application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainActivityViewModel.class))
            return ((T) new MainActivityViewModel(application));
        else{
            throw new IllegalArgumentException("Unable to construct mainActivityViewmodel");
        }
    }
}
