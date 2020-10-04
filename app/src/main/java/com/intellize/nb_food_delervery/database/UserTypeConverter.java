package com.intellize.nb_food_delervery.database;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellize.nb_food_delervery.model.User;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class UserTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static User stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<User>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(User someObjects) {
        return gson.toJson(someObjects);
    }
}
