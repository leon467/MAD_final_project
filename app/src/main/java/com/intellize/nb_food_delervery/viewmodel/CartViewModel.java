package com.intellize.nb_food_delervery.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.database.entity.Driver;
import com.intellize.nb_food_delervery.model.CartUI;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.utility.AlertType;
import com.intellize.nb_food_delervery.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.intellize.nb_food_delervery.database.Firebase.driverRef;
import static com.intellize.nb_food_delervery.database.Firebase.orderRef;

public class CartViewModel extends BaseViewModel {

    public MutableLiveData<CartUI> cart = new MutableLiveData<>();
    public MutableLiveData<Driver> driver = new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadItems(LifecycleOwner owner) {

        final CartUI cartUI = new CartUI();

        localDatabase.cartDao().getLiveAllItems().observe(owner, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                cartUI.setCartList(cartList);
                cartUI.setQty(localDatabase.cartDao().getCartTotalQty());
                cartUI.setAmount(localDatabase.cartDao().getCartValue() == null ? 0.0 : localDatabase.cartDao().getCartValue());

                cart.postValue(cartUI);
            }
        });
    }


    public void getDriver(final Activity activity) {

        onLoading.postValue(true);
        final List<Driver> drivers = new ArrayList<>();

        driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Driver driver = dataSnapshot.getValue(Driver.class);

                    if (driver != null) {
                        drivers.add(driver);
                    }

                }

                onLoading.postValue(false);
                if (drivers.isEmpty()) {
                    Utils.showMessage(activity, AlertType.ERROR, "Please add drivers before run the app !");
                    return;
                } else if (drivers.size() > 1) {

                    Random random = new Random();

                    int min = 0;
                    int max = drivers.size() - 1;

                    Driver selectedRandomDriver = drivers.get(random.nextInt(max-min) + min);
                    driver.postValue(selectedRandomDriver);
                } else {
                    driver.postValue(drivers.get(0));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onLoading.postValue(false);
                onError.postValue(error.getMessage());
            }
        });

    }


    public void checkout(Order order) {
        onLoading.postValue(true);
        String id = Utils.generateId();
        order.setDate(Utils.getTime());
        order.setOrderId(id);
        orderRef.child(id).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void clearCart() {
        localDatabase.cartDao().clearAll();
    }


}
