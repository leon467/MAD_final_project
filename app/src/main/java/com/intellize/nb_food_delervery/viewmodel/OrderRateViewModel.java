package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.intellize.nb_food_delervery.model.Order;

import static com.intellize.nb_food_delervery.database.Firebase.orderRef;

public class OrderRateViewModel extends BaseViewModel {

    public OrderRateViewModel(@NonNull Application application) {
        super(application);
    }


    public void submit(Order order) {
        onLoading.postValue(true);
        orderRef.child(order.getOrderId()).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onLoading.postValue(false);
                onSuccess.postValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onLoading.postValue(false);
                onError.postValue(e.getLocalizedMessage());
            }
        });
    }
}
