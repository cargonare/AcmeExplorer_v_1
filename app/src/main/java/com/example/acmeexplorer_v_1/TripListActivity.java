package com.example.acmeexplorer_v_1;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Switch;

import com.example.acmeexplorer_v_1.adapters.TripsAdapter;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;


public class TripListActivity extends AppCompatActivity implements TripsAdapter.OnTripListener {
    private ArrayList<Trip> trips = new ArrayList<>();
    private ArrayList<Trip> selectedTrips = new ArrayList<>();
    private ArrayList<Trip> filteredTrips = new ArrayList<>();

    private Switch switchColumns;
    private Button filterButton;
    private TripsAdapter tripsAdapter;
    private RecyclerView rvTripList;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        rvTripList = findViewById(R.id.rvTripList);
        switchColumns = findViewById(R.id.switchCols);
        filterButton = findViewById(R.id.filterButton);

        try {
            sharedPreferences = getSharedPreferences("com.example.acmeexplorer_v_1", MODE_PRIVATE);

            boolean tripsGenerated = sharedPreferences.getBoolean("trips-generated", false);
            System.out.println("El numero de viajes generados " + tripsGenerated);
            if (tripsGenerated==false) {
                // Generar la lista de viajes y guardarla en las SharedPreferences
                trips = Trip.generarViajes();
                StringBuilder tripsJson = new StringBuilder();
                for (Trip trip : trips) {
                    tripsJson.append(trip.getId()).append(",")
                            .append(trip.getCiudadProcedencia()).append(",")
                            .append(trip.getCiudadDestino()).append(",")
                            .append(trip.getPrecio()).append(",")
                            .append(trip.getFechaIda().toString()).append(",")
                            .append(trip.getFechaVuelta().toString()).append(",")
                            .append(trip.getSeleccionar()).append(",")
                            .append(trip.getUrlImagenes()).append(";");
                }
                System.out.println(tripsJson);
                sharedPreferences.edit().putString("trip-data", tripsJson.toString()).apply();

                sharedPreferences.edit().putBoolean("trips-generated", true).apply();
            } else {
                String tripsJsonString = sharedPreferences.getString("trip-data", "");
                ArrayList<Trip> tripsRecuperados = new ArrayList<>();
                if (!tripsJsonString.equals("")) {
                    JSONArray jsonArray = new JSONArray(tripsJsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Trip tripRecuperado = new Trip();
                        tripRecuperado.setId(jsonObject.getInt("id"));
                        tripRecuperado.setCiudadProcedencia(jsonObject.getString("ciudadProcedencia"));
                        tripRecuperado.setCiudadDestino(jsonObject.getString("ciudadDestino"));
                        tripRecuperado.setPrecio(jsonObject.getDouble("precio"));
                        tripRecuperado.setFechaIda(LocalDate.parse(jsonObject.getString("fechaIda")));
                        tripRecuperado.setFechaVuelta(LocalDate.parse(jsonObject.getString("fechaVuelta")));
                        tripRecuperado.setSeleccionar(jsonObject.getBoolean("seleccionar"));
                        tripRecuperado.setUrlImagenes(jsonObject.getString("urlImagenes"));
                        tripsRecuperados.add(tripRecuperado);
                        System.out.println(tripsRecuperados);
                    }
                    trips=tripsRecuperados;
                }
            }


        } catch (Exception e) {

        }


        tripsAdapter = new TripsAdapter(trips, this);
        rvTripList.setAdapter(tripsAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rvTripList.setLayoutManager(gridLayoutManager);

        switchColumns.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                gridLayoutManager.setSpanCount(2);
            } else {
                gridLayoutManager.setSpanCount(1);
            }
        });

        filterButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), FilterActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Double filterMinPrice = Double.parseDouble(data.getStringExtra("filterMinPrice"));
                    Double filterMaxPrice = Double.parseDouble(data.getStringExtra("filterMaxPrice"));
                    LocalDate filterMinDate = LocalDate.parse(data.getStringExtra("filterMinDate"));
                    LocalDate filterMaxDate = LocalDate.parse(data.getStringExtra("filterMaxDate"));

                    for (int i = 0; i < trips.size(); i++) {
                        Trip trip = trips.get(i);

                        Boolean validMinPrice = trip.getPrecio().compareTo(filterMinPrice) >= 0;
                        Boolean validMaxPrice = trip.getPrecio().compareTo(filterMaxPrice) <= 0;

                        Boolean validStartDate = trip.getFechaIda().isAfter(filterMinDate) || trip.getFechaIda().isEqual(filterMinDate);
                        Boolean validEndDate = trip.getFechaVuelta().isBefore(filterMaxDate) || trip.getFechaVuelta().isEqual(filterMaxDate);

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

        if (trip.getSeleccionar()) {
            selectedTrips.add(trip);
        } else {
            Iterator<Trip> iterator = selectedTrips.iterator();
            while (iterator.hasNext()) {
                Trip selectedTrip = iterator.next();
                if (selectedTrip.getId() == trip.getId()) {
                    iterator.remove();
                }
            }
        }

        JSONArray tripsJsonArray = new JSONArray();
        for (Trip t : trips) {
            try {
                JSONObject tripJsonObject = new JSONObject();
                tripJsonObject.put("id", t.getId());
                tripJsonObject.put("ciudadProcedencia", t.getCiudadProcedencia());
                tripJsonObject.put("ciudadDestino", t.getCiudadDestino());
                tripJsonObject.put("precio", t.getPrecio());
                tripJsonObject.put("fechaIda", t.getFechaIda().toString());
                tripJsonObject.put("fechaVuelta", t.getFechaVuelta().toString());
                tripJsonObject.put("seleccionar", t.getSeleccionar());
                tripJsonObject.put("urlImagenes", t.getUrlImagenes());
                tripsJsonArray.put(tripJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sharedPreferences.edit().putString("trip-data", tripsJsonArray.toString()).apply();

        JSONArray selectedTripsJsonArray = new JSONArray();
        for (Trip t : selectedTrips) {
            try {
                JSONObject tripJsonObject = new JSONObject();
                tripJsonObject.put("id", t.getId());
                tripJsonObject.put("ciudadProcedencia", t.getCiudadProcedencia());
                tripJsonObject.put("ciudadDestino", t.getCiudadDestino());
                tripJsonObject.put("precio", t.getPrecio());
                tripJsonObject.put("fechaIda", t.getFechaIda().toString());
                tripJsonObject.put("fechaVuelta", t.getFechaVuelta().toString());
                tripJsonObject.put("seleccionar", t.getSeleccionar());
                System.out.println("El seleccionar está " + t.getSeleccionar());
                tripJsonObject.put("urlImagenes", t.getUrlImagenes());
                selectedTripsJsonArray.put(tripJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sharedPreferences.edit().putString("selected-trip-data", selectedTripsJsonArray.toString()).apply();

        tripsAdapter.notifyDataSetChanged();
    }



}