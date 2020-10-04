package com.intellize.nb_food_delervery.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.intellize.nb_food_delervery.model.Food;

import java.util.ArrayList;
import java.util.List;

import static com.intellize.nb_food_delervery.database.Firebase.foodRef;

public class HomeViewModel extends BaseViewModel {

    public MutableLiveData<List<Food>> foodList = new MutableLiveData<>();
    public MutableLiveData<Double> cartTotal = new MutableLiveData<>();


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadFoods() {

        onLoading.postValue(true);
        final List<Food> list = new ArrayList<>();

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    food.setId(dataSnapshot.getKey());
                    list.add(food);
                }

                onLoading.postValue(false);
                foodList.postValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onLoading.postValue(false);
                onError.postValue(error.getMessage());
            }
        });
    }


    public void getCartTotal(LifecycleOwner owner) {
        localDatabase.cartDao().getLiveCartValue().observe(owner, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble != null) {
                    cartTotal.postValue(aDouble);
                } else {
                    cartTotal.postValue(0.0);
                }
            }
        });
    }


    public void logout() {
        localDatabase.clearAllTables();
    }
}
