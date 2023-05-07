package com.example.acmeexplorer_v_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.material.snackbar.Snackbar;

public class TripDetailActivity extends AppCompatActivity {
    private ImageView ivImage, ivIcon;
    private TextView tvStartDate, tvEndDate, tvStartCity, tvEndCity, tvPrice;
    private Button payButton;
    private FirestoreService fireStoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        ivImage = findViewById(R.id.ivEndCityImage);
        tvStartCity = findViewById(R.id.tvSelectedStartCity);
        tvEndCity = findViewById(R.id.tvSelectedEndCity);
        tvPrice = findViewById(R.id.tvSelectedPrice);
        tvStartDate = findViewById(R.id.tvSelectedStartDate);
        tvEndDate = findViewById(R.id.tvSelectedEndDate);
        ivIcon = findViewById(R.id.ivSelectedIcon);
        payButton = findViewById(R.id.button2);

        Trip trip = (Trip) getIntent().getSerializableExtra("trip");

        Glide.with(this).load(trip.getUrlImagenes()).into(ivImage);
        tvStartCity.setText("Sale desde: " + trip.getCiudadProcedencia());
        tvEndCity.setText(trip.getCiudadDestino());
        tvStartDate.setText("Fecha de Ida: " + trip.getFechaIda());
        tvEndDate.setText("Fecha de Vuelta: " + trip.getFechaVuelta());
        tvPrice.setText("Precio: " + trip.getPrecio() + "€");

        ivIcon.setImageResource(trip.getSeleccionar() ? R.drawable.green_tick : R.drawable.red_cross);
        payButton.setVisibility(trip.getSeleccionar() ? View.VISIBLE : View.INVISIBLE);

        ivIcon.setOnClickListener(view -> {
            trip.setSeleccionar(!trip.getSeleccionar());
            fireStoreService.selectTrip(trip.getId(), trip.getSeleccionar()).addOnSuccessListener(queryDocumentSnapshots -> {
                ivIcon.setImageResource(trip.getSeleccionar() ? R.drawable.green_tick : R.drawable.red_cross);
                payButton.setVisibility(trip.getSeleccionar() ? View.VISIBLE : View.INVISIBLE);
            }).addOnFailureListener(e -> {
                Snackbar.make(view, "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            });
        });

    }

    public void comprarTicket(View view) {
        Toast.makeText(this, "Buy Logic goes here", Toast.LENGTH_SHORT).show();
    }
}