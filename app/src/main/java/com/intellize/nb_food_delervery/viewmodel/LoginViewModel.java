package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;
import com.intellize.nb_food_delervery.model.User;

import static com.intellize.nb_food_delervery.database.Firebase.userRef;

public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public void login(final String username, final String password) {


//        if(userRef==null){
//            onError.postValue("Username password does not matched !");
//            return;
//        }
        Log.e("aa","00");
        onLoading.postValue(true);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isMatched = false;
                Log.e("aa","1");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.e("aa","2");
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        Log.e("aa","3");
                        if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                            isMatched = true;
                            Log.e("aa","4");
                            localDatabase.currentUserDao().clearAll();
                            localDatabase.currentUserDao().save(new CurrentUser(user));
                            onLoading.postValue(false);
                            onSuccess.postValue(true);
                            return;
                        }
                    }
                }

                if (!isMatched) {
                    onLoading.postValue(false);
                    onError.postValue("Username password does not matched !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onLoading.postValue(false);
                onError.postValue(error.getMessage());
            }
        });


    }


}
