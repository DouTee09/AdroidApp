package com.example.studentmanagement;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance("https://studentmanagement-fa9b0-default-rtdb.asia-southeast1.firebasedatabase.app/").setPersistenceEnabled(true);
    }

}
