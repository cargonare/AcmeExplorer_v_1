package com.example.acmeexplorer_v_1;

import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreService {
    private static String userId;
    private static FirebaseFirestore mDatabase;
    private static FirestoreService service;

    public static FirestoreService getServiceInstance(){
        if (service == null || mDatabase == null) {
            service = new FirestoreService();
            mDatabase = FirebaseFirestore.getInstance();
        }

        if (userId == null || userId.isEmpty()) {
            userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
        }

        return service;
    }

    public Task<DocumentReference> saveTrip(Trip trip){
        return mDatabase.collection("user").document(userId).collection("trip").add(trip);
    }

    public void getTrip(String id, EventListener<DocumentSnapshot> snapshotListener) {
        mDatabase.collection("user").document(userId).collection("trip").document(id).addSnapshotListener(snapshotListener);
    }

    public Task<Void> selectTrip(String id, boolean seleccionar) {
        return mDatabase.collection("user").document(userId).collection("trip").document(id).update("seleccionar", seleccionar);
    }

    public Task<QuerySnapshot> getTrips() {
        return mDatabase.collection("user").document(userId).collection("trip").get();
    }

    public Task<QuerySnapshot> getSelectedTrips() {
        return mDatabase.collection("user").document(userId).collection("trip").whereEqualTo("seleccionar", true).get();
    }

    public Task<QuerySnapshot> getFilteredTrips(ArrayList<Filter> filters) {
        return mDatabase.collection("user").document(userId).collection("trip").orderBy("endCity").limit(3).get();
    }
}