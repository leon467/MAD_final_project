package com.intellize.nb_food_delervery.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.intellize.nb_food_delervery.database.entity.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void save(Card card);

    @Query("select * from card")
    LiveData<List<Card>> getLiveAllCards();
}
