package com.dalakoti07.foodrecipeapp.network;

import com.google.gson.annotations.SerializedName;

public class FoodRecipe {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("category")
    private String category;

    @SerializedName("label")
    private String label;

    @SerializedName("price")
    private String price;

    @SerializedName("description")
    private String description;

    @Override
    public String toString() {
        return "FoodRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
