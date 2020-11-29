package com.dalakoti07.foodrecipeapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={FoodDatabaseModel.class},version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    abstract FoodDao foodDao();

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
