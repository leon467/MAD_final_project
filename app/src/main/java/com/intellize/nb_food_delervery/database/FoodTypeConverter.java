package com.intellize.nb_food_delervery.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellize.nb_food_delervery.model.Food;
import com.intellize.nb_food_delervery.model.User;

import java.lang.reflect.Type;

public class FoodTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static Food stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Food>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(Food someObjects) {
        return gson.toJson(someObjects);
    }
}
