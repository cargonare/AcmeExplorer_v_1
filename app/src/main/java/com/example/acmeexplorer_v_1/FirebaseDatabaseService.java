package com.example.acmeexplorer_v_1;

import com.example.acmeexplorer_v_1.models.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseService {
    private static String userId;
    private static FirebaseDatabaseService service;
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabaseService getServiceInstance() {
        if (service == null || mDatabase == null) {
            service = new FirebaseDatabaseService();
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        if (userId == null || userId.isEmpty()) {
            userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
        }

        return service;
    }

    public DatabaseReference getTrip(String tripId) {
        return FirebaseDatabase.getInstance().getReference("user").child(userId).child("trip").child(tripId).getRef();
    }


    public DatabaseReference getTrips(){
        return FirebaseDatabase.getInstance().getReference("user").child(userId).child("trip").getRef();
    }

    public void saveTrip(Trip trip, DatabaseReference.CompletionListener listener) {
        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference("user").child(userId).child("trip");
        tripsRef.push().setValue(trip, listener);
    }




}
