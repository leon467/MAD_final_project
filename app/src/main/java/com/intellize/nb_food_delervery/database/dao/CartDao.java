package com.intellize.nb_food_delervery.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.intellize.nb_food_delervery.database.entity.Cart;

import java.util.List;

@Dao
public interface CartDao {


    @Insert
    void save(Cart cart);


    @Update
    void update(Cart cart);

    @Query("select * from cart")
    List<Cart> getAllItems();


    @Query("select * from cart")
    LiveData<List<Cart>> getLiveAllItems();

    @Query("delete from cart")
    void clearAll();

    @Query("delete from cart where primaryKey =:pk")
    void delete(int pk);

    @Query("Select * from cart where primaryKey =:pk")
    Cart getCartItem(int pk);

    @Query("Select * from cart where `key` =:key")
    Cart getCartItem(String key);

    @Query("select sum(value) from cart")
    LiveData<Double> getLiveCartValue();


    @Query("select sum(value) from cart")
    Double getCartValue();


    @Query("select sum(qty) from cart")
    int getCartTotalQty();




}
