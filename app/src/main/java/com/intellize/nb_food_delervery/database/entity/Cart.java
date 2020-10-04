package com.intellize.nb_food_delervery.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.intellize.nb_food_delervery.database.FoodTypeConverter;
import com.intellize.nb_food_delervery.model.Food;

@Entity
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    @TypeConverters(FoodTypeConverter.class)
    private Food food;

    private String key;

    private int qty;

    private double value;

    public Cart() {
    }

    public Cart(Food food, String key, int qty, double value) {
        this.food = food;
        this.key = key;
        this.qty = qty;
        this.value = value;
    }


    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
