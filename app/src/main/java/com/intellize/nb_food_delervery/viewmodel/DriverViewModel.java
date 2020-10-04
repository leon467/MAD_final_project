package com.intellize.nb_food_delervery.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.intellize.nb_food_delervery.database.entity.Driver;
import com.intellize.nb_food_delervery.utility.AlertType;
import com.intellize.nb_food_delervery.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.intellize.nb_food_delervery.database.Firebase.driverRef;

public class DriverViewModel extends BaseViewModel {

    public MutableLiveData<List<Driver>> driverList = new MutableLiveData<>();

    public DriverViewModel(@NonNull Application application) {
        super(application);
    }


    public void saveDriver(Driver driver) {
        onLoading.postValue(true);

        driverRef.child(driverRef.push().getKey()).setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void getDriver() {

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
                driverList.postValue(drivers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onLoading.postValue(false);
                onError.postValue(error.getMessage());
            }
        });

    }
}
