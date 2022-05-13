package com.example.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adding {
    private DatabaseReference databaseReference;
    public Adding()
    {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User_data.class.getSimpleName());
    }
    public Task<Void> add(User_data emp){

        return databaseReference.push().setValue(emp);
    }
}
