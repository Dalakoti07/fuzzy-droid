package com.dalakoti07.foodrecipeapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities ={FoodDatabaseModel.class},version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile RecipeDatabase INSTANCE;

    public static RecipeDatabase getRecipeDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, "food_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
