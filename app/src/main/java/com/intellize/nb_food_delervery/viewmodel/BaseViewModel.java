package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;

public class BaseViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> onSuccess = new MutableLiveData<>();
    public MutableLiveData<String> onError = new MutableLiveData<>();
    public MutableLiveData<Boolean> onLoading = new MutableLiveData<>();
    protected LocalDatabase localDatabase;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        localDatabase = LocalDatabase.getInstance(application);
    }


    public CurrentUser getCurrentUser() {
        return localDatabase.currentUserDao().getUser();
    }
}
