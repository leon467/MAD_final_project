package com.intellize.nb_food_delervery.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    private String name;
    private String cardNumber;
    private String bank;

    public Card(String name, String cardNumber, String bank) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.bank = bank;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
