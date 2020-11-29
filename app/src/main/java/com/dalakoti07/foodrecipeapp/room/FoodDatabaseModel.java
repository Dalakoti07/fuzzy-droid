package com.dalakoti07.foodrecipeapp.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Food_Table")
public class FoodDatabaseModel {
    @PrimaryKey
    private int id;

    private String name;

    private String image;

    private String category;

    private String label;

    private String price;

    private String description;

    private Boolean isFavourite;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "FoodRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public FoodDatabaseModel(int id, String name, String image, String category, String label, String price, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.label = label;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }
}
