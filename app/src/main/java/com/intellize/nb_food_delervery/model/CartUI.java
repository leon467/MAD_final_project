package com.intellize.nb_food_delervery.model;

import com.intellize.nb_food_delervery.database.entity.Cart;

import java.util.List;

public class CartUI {

    private List<Cart> cartList;
    private double amount;
    private int qty;




    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
