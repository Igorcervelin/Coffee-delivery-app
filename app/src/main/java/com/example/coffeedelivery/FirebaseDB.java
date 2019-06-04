package com.example.coffeedelivery;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference database;

    private FirebaseDB(){} // impede instancia dessa classe

    public static DatabaseReference getDatabaseReference(){
        if(database == null){
            database = FirebaseDatabase.getInstance().getReference();
        }

        return database;
    }

    public static FirebaseDatabase getFirebaseDatabase(){
        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return firebaseDatabase;
    }
}
