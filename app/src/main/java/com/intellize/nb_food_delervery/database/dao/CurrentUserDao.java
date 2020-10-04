package com.intellize.nb_food_delervery.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.intellize.nb_food_delervery.database.entity.CurrentUser;

@Dao
public interface CurrentUserDao {

    @Insert
    void save(CurrentUser user);

    @Update
    void update(CurrentUser user);

    @Query("delete from currentuser")
    void clearAll();

    @Query("select * from currentuser")
    CurrentUser getUser();

}
