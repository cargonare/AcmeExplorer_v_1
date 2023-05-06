package com.example.acmeexplorer_v_1;


import static com.example.acmeexplorer_v_1.Imports.ArrayTrips;
import static com.example.acmeexplorer_v_1.Imports.gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmeexplorer_v_1.adapters.TripsAdapter;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

public class SelectedTripListActivity extends AppCompatActivity implements TripsAdapter.OnTripListener {
    private ArrayList<Trip> trips;
    private ArrayList<Trip> selectedTrips;
    private TripsAdapter tripsAdapter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_trip_list);

        RecyclerView rvSelectedTripList = findViewById(R.id.rvSelectedTripList);

        try {
            sharedPreferences = getSharedPreferences("cargonare1", MODE_PRIVATE);
            String json = sharedPreferences.getString("list_selected", "{}");
            String trips_json = sharedPreferences.getString("list_trip", "{}");

            selectedTrips = json == "{}" ? new ArrayList<>() : gson.fromJson(json, ArrayTrips);

            if (trips_json != "{}") {
                trips = gson.fromJson(trips_json, ArrayTrips);
            }
        } catch (Exception e) {

        }

        tripsAdapter = new TripsAdapter(selectedTrips, this);
        rvSelectedTripList.setAdapter(tripsAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rvSelectedTripList.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onTripClick(int position) {
        Intent intent = new Intent(this, TripDetailActivity.class);
        intent.putExtra("trip", selectedTrips.get(position));
        startActivity(intent);
    }

    @Override
    public void onSelectTrip(int position) {
        Trip selectedTrip = selectedTrips.get(position);

        selectedTrip.setSeleccionar(false);

        for(int i = 0; i < trips.size(); i++) {
            Trip trip = trips.get(i);
            if(trip.equals(selectedTrip)){
                trips.get(i).setSeleccionar(false);
            }
        }

        selectedTrips.remove(selectedTrip);

        sharedPreferences.edit().putString("list_selected", gson.toJson(selectedTrips)).apply();
        sharedPreferences.edit().putString("list_trip", gson.toJson(trips)).apply();

        tripsAdapter.notifyDataSetChanged();
    }
}