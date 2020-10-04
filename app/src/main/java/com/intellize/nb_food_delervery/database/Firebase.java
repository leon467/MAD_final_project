package com.intellize.nb_food_delervery.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    public static DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");
    public static DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("order");
    public static DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference("driver");
    public static DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("food");
}
