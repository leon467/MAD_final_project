package com.intellize.nb_food_delervery.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.intellize.nb_food_delervery.database.UserTypeConverter;
import com.intellize.nb_food_delervery.model.User;

@Entity
public class CurrentUser {

    @PrimaryKey(autoGenerate = true)
    int primaryKey;

    @TypeConverters(UserTypeConverter.class)
    private User user;

    public CurrentUser(User user) {
        this.user = user;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
