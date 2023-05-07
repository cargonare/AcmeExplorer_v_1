package com.example.acmeexplorer_v_1;

import static com.example.acmeexplorer_v_1.Imports.transformarFecha;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.acmeexplorer_v_1.adapters.TripsAdapter;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripListActivity extends AppCompatActivity implements TripsAdapter.OnTripListener {

    protected final SimplifyActivity<Intent, ActivityResult> activityLauncher = SimplifyActivity.registerActivityForResult(this);
    private FirestoreService firestoreService;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<Trip> trips = new ArrayList<>();
    private ArrayList<Trip> filteredTrips = new ArrayList<>();

    private Switch switchColumns;
    private Button filterButton, newTripButton;
    private TripsAdapter tripsAdapter;
    private RecyclerView rvTripList;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        firestoreService = FirestoreService.getServiceInstance();

        rvTripList = findViewById(R.id.rvTripList);
        switchColumns = findViewById(R.id.switchCols);
        filterButton = findViewById(R.id.filterButton);
        loadingPB = findViewById(R.id.loadingPB);
        newTripButton = findViewById(R.id.addTripButton);

        tripsAdapter = new TripsAdapter(trips, this);
        rvTripList.setAdapter(tripsAdapter);
        gridLayoutManager = new GridLayoutManager(this, 1);
        rvTripList.setLayoutManager(gridLayoutManager);

        cargarViajes();

        switchColumns.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                gridLayoutManager.setSpanCount(2);
            } else {
                gridLayoutManager.setSpanCount(1);
            }
        });

        filterButton.setOnClickListener(view -> filterTrips());
        newTripButton.setOnClickListener(view -> addTrip());
    }

    @Override
    public void onTripClick(int position) {
        Intent intent = new Intent(this, TripDetailActivity.class);
        intent.putExtra("trip", trips.get(position));
        startActivity(intent);
    }

    @Override
    public void onSelectTrip(int position) {
        Trip trip = trips.get(position);

        trip.setSeleccionar(!trip.getSeleccionar());
        firestoreService.selectTrip(trip.getId(), trip.getSeleccionar()).addOnSuccessListener(queryDocumentSnapshots -> {
            Snackbar.make(rvTripList, "Success!", Snackbar.LENGTH_SHORT).show();
            tripsAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Snackbar.make(rvTripList, "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        });
    }

    public void cargarViajes() {
        trips.clear();

        firestoreService.getTrips().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
            for(DocumentSnapshot snapshot: documentSnapshotList){
                Trip trip = snapshot.toObject(Trip.class);
                trip.setId(snapshot.getId());
                trips.add(trip);
                System.out.println("viendo viajes" + trips);
            }
            loadingPB.setVisibility(View.GONE);
            tripsAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Snackbar.make(rvTripList, "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        });
    }

    public void addTrip() {
        Intent intent  = new Intent(this, NewTripActivity.class);
        activityLauncher.launch(intent, result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                String documentId = result.getData().getStringExtra("documentReference");
                Snackbar.make(rvTripList, "Trip " + documentId + " added successfully!", Snackbar.LENGTH_SHORT).show();
                cargarViajes();
            }
        });
    }

    public void filterTrips() {
        Intent intent = new Intent(this, FilterActivity.class);
        activityLauncher.launch(intent, result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                Double filterMinPrice = Double.parseDouble(data.getStringExtra("filterMinPrice"));
                Double filterMaxPrice = Double.parseDouble(data.getStringExtra("filterMaxPrice"));
                Date filterMinDate = transformarFecha(data.getStringExtra("filterMinDate"));
                Date filterMaxDate = transformarFecha(data.getStringExtra("filterMaxDate"));

                for (int i = 0; i < trips.size(); i++) {
                    Trip trip = trips.get(i);

                    Boolean validMinPrice = trip.getPrecio() >= filterMinPrice;
                    Boolean validMaxPrice = trip.getPrecio() <= filterMaxPrice;

                    Boolean validStartDate = trip.getFechaIda().compareTo(filterMinDate) > 0 || trip.getFechaIda().compareTo(filterMinDate) == 0;
                    Boolean validEndDate = trip.getFechaIda().compareTo(filterMaxDate) < 0 || trip.getFechaIda().compareTo(filterMaxDate) == 0;

                    if ((validMinPrice && validMaxPrice) || (validStartDate && validEndDate)) {
                        filteredTrips.add(trip);
                    }
                }

                if (filteredTrips.size() > 0) {
                    trips.clear();
                    trips.addAll(filteredTrips);
                    tripsAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(rvTripList, "No se encontraron viajes con los filtros seleccionados", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}