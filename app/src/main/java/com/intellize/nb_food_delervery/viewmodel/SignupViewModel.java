package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.intellize.nb_food_delervery.database.Firebase;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;
import com.intellize.nb_food_delervery.model.User;

import static com.intellize.nb_food_delervery.database.Firebase.userRef;

public class SignupViewModel extends BaseViewModel {



    public SignupViewModel(@NonNull Application application) {
        super(application);
    }


    public void signup(final User user) {

        onLoading.postValue(true);
        String key = userRef.push().getKey();
        user.setId(key);


        userRef.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                localDatabase.currentUserDao().save(new CurrentUser(user));
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
