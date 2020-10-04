package com.intellize.nb_food_delervery.model;

import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.database.entity.Driver;

import java.util.List;

public class Order {

    private String orderId;
    private User user;
    private String paymentMethod;
    private String date;
    private List<Cart> foodList;
    private double totalAmount;
    private Driver driver;
    private double rating;
    private String review;
    private boolean isRated;

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Cart> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Cart> foodList) {
        this.foodList = foodList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }
}
