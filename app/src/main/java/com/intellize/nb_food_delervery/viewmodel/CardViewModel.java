package com.intellize.nb_food_delervery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.intellize.nb_food_delervery.database.entity.Card;

import java.util.List;

public class CardViewModel extends BaseViewModel {

    public MutableLiveData<List<Card>> cardList = new MutableLiveData<>();

    public CardViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadCards(LifecycleOwner owner) {
        onLoading.postValue(true);

        localDatabase.cardDao().getLiveAllCards().observe(owner, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                onLoading.postValue(false);
                cardList.postValue(cards);
            }
        });
    }


    public void saveCard(Card card) {
        localDatabase.cardDao().save(card);

    }


}
