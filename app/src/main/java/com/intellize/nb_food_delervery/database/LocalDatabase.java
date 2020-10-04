package com.intellize.nb_food_delervery.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.intellize.nb_food_delervery.database.dao.CardDao;
import com.intellize.nb_food_delervery.database.dao.CartDao;
import com.intellize.nb_food_delervery.database.dao.CurrentUserDao;
import com.intellize.nb_food_delervery.database.entity.Card;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;


@Database(entities = {CurrentUser.class, Cart.class, Card.class}, exportSchema = false, version = 31)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase instance;
    private static final String DB_Name = "foodApp";

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, DB_Name)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    /*
       Daos
       _________________________________________________________________________________________________
        */
    public abstract CurrentUserDao currentUserDao();
    public abstract CartDao cartDao();
    public abstract CardDao cardDao();


}
