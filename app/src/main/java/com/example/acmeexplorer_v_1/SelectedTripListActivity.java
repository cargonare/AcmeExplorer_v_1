package com.example.acmeexplorer_v_1;


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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            String json = sharedPreferences.getString("selected-trip-data", "{}");
            String trips_json = sharedPreferences.getString("trip-data", "{}");

            selectedTrips = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String ciudadProcedencia = jsonObject.getString("ciudadProcedencia");
                    String ciudadDestino = jsonObject.getString("ciudadDestino");
                    Double precio = jsonObject.getDouble("precio");
                    String fechaIdaStr = jsonObject.getString("fechaIda");
                    LocalDate fechaIda = LocalDate.parse(fechaIdaStr);
                    String fechaVueltaStr = jsonObject.getString("fechaVuelta");
                    LocalDate fechaVuelta = LocalDate.parse(fechaVueltaStr);
                    Boolean seleccionar = jsonObject.getBoolean("seleccionar");
                    String urlImagenes = jsonObject.getString("urlImagenes");
                    Trip trip = new Trip(id, ciudadProcedencia, ciudadDestino, precio, fechaIda, fechaVuelta, seleccionar, urlImagenes);
                    selectedTrips.add(trip);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            trips = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(trips_json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String ciudadProcedencia = jsonObject.getString("ciudadProcedencia");
                    String ciudadDestino = jsonObject.getString("ciudadDestino");
                    Double precio = jsonObject.getDouble("precio");
                    String fechaIdaStr = jsonObject.getString("fechaIda");
                    LocalDate fechaIda = LocalDate.parse(fechaIdaStr);
                    String fechaVueltaStr = jsonObject.getString("fechaVuelta");
                    LocalDate fechaVuelta = LocalDate.parse(fechaVueltaStr);
                    Boolean seleccionar = jsonObject.getBoolean("seleccionar");
                    String urlImagenes = jsonObject.getString("urlImagenes");
                    Trip trip = new Trip(id, ciudadProcedencia, ciudadDestino, precio, fechaIda, fechaVuelta, seleccionar, urlImagenes);
                    trips.add(trip);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
        Trip selectTrip = selectedTrips.get(position);

        //selectTrip.setSeleccionar(true);

        for(int i = 0; i < trips.size(); i++) {
            if(trips.get(i).getId()==selectTrip.getId()){
                trips.get(i).setSeleccionar(false);
            }
        }

        selectedTrips.remove(selectTrip);

        JSONArray tripsJsonArray = new JSONArray();
        for (Trip trip : trips) {
            try {
                JSONObject tripJsonObj = new JSONObject();
                tripJsonObj.put("id", trip.getId());
                tripJsonObj.put("ciudadProcedencia", trip.getCiudadProcedencia());
                tripJsonObj.put("ciudadDestino", trip.getCiudadDestino());
                tripJsonObj.put("precio", trip.getPrecio());
                tripJsonObj.put("fechaIda", trip.getFechaIda().toString());
                tripJsonObj.put("fechaVuelta", trip.getFechaVuelta().toString());
                tripJsonObj.put("seleccionar", trip.getSeleccionar());
                tripJsonObj.put("urlImagenes", trip.getUrlImagenes());
                tripsJsonArray.put(tripJsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String tripsJsonString = tripsJsonArray.toString();

        JSONArray selectedTripsJsonArray = new JSONArray();
        for (Trip selectedTrip : selectedTrips) {
            try {
                JSONObject selectedTripJsonObj = new JSONObject();
                selectedTripJsonObj.put("id", selectedTrip.getId());
                selectedTripJsonObj.put("ciudadProcedencia", selectedTrip.getCiudadProcedencia());
                selectedTripJsonObj.put("ciudadDestino", selectedTrip.getCiudadDestino());
                selectedTripJsonObj.put("precio", selectedTrip.getPrecio());
                selectedTripJsonObj.put("fechaIda", selectedTrip.getFechaIda().toString());
                selectedTripJsonObj.put("fechaVuelta", selectedTrip.getFechaVuelta().toString());
                selectedTripJsonObj.put("seleccionar", selectedTrip.getSeleccionar());
                selectedTripJsonObj.put("urlImagenes", selectedTrip.getUrlImagenes());
                selectedTripsJsonArray.put(selectedTripJsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String selectedTripsJsonString = selectedTripsJsonArray.toString();

        sharedPreferences.edit().putString("trip-data", tripsJsonString).apply();
        sharedPreferences.edit().putString("selected-trip-data", selectedTripsJsonString).apply();

        tripsAdapter.notifyDataSetChanged();

    }
}