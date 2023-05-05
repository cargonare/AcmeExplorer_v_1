package com.example.acmeexplorer_v_1;


import static com.example.acmeexplorer_v_1.Imports.ArrayTrips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmeexplorer_v_1.adapters.TripsAdapter;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

public class SelectedTripListActivity extends AppCompatActivity implements TripsAdapter.OnTripListener {
    private ArrayList<Trip> trips;
    private ArrayList<Trip> selectedTrips;
    private TripsAdapter tripsAdapter;

    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_trip_list);

        RecyclerView rvSelectedTripList = findViewById(R.id.rvSelectedTripList);

        try {
            sharedPreferences = getSharedPreferences("com.example.acmeexplorer_v_1", MODE_PRIVATE);
//            sharedPreferences.edit().clear().commit();
            String json = sharedPreferences.getString("selected-trip-data", "{}");
            String trips_json = sharedPreferences.getString("trip-data", "{}");

            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new Imports.LocalDateConverter())
                    .create();

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
        Intent intent = new Intent(this, SelectedTripDetailActivity.class);
        intent.putExtra("selected_trip", selectedTrips.get(position));
        startActivity(intent);
    }

    @Override
    public void onSelectTrip(int position) {
        Trip selectedTrip = selectedTrips.get(position);

        selectedTrip.setSeleccionar(false);

        for(int i = 0; i < trips.size(); i++) {
            if(trips.get(i).getId()==selectedTrip.getId()){
                trips.get(i).setSeleccionar(false);
            }
        }

        selectedTrips.remove(selectedTrip);

        sharedPreferences.edit().putString("selected-trip-data", gson.toJson(selectedTrips)).apply();
        sharedPreferences.edit().putString("trip-data", gson.toJson(trips)).apply();

        tripsAdapter.notifyDataSetChanged();
    }
}