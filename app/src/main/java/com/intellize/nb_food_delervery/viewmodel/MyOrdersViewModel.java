package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import static com.intellize.nb_food_delervery.database.Firebase.orderRef;

public class MyOrdersViewModel extends BaseViewModel {


    public MutableLiveData<List<Order>> orderList = new MutableLiveData<>();

    public MyOrdersViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadOrders() {


        onLoading.postValue(true);
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order != null) {
                        if (order.getUser().getEmail().equals(HomeActivity.getCurrentUser().getUser().getEmail())) {
                            orders.add(order);
                        }
                    }
                }

                onLoading.postValue(false);
                orderList.postValue(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onLoading.postValue(false);
                onError.postValue(error.getMessage());
            }
        });
    }

}
