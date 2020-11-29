package com.dalakoti07.foodrecipeapp.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FoodDatabaseModel> foods);

    @Query("SELECT * from Food_Table")
    LiveData<List<FoodDatabaseModel>> getAllFoods();

    @Query("SELECT * from Food_Table where isFavourite ")
    LiveData<List<FoodDatabaseModel>> getFavouriteFoods();

    // this below would server as
    @Update
    void addFoodToFavourite(FoodDatabaseModel foodDatabaseModel);

    @Update
    void removeAllFoodsFromFav(FoodDatabaseModel... foods);
}
