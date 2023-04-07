package com.example.acmeexplorer_v_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.material.snackbar.Snackbar;

import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SelectedTripDetailActivity extends AppCompatActivity {
    private ImageView ivImage, ivIcon;
    private TextView tvStartDate, tvEndDate, tvStartCity, tvEndCity, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_trip_detail);

        ivImage = findViewById(R.id.ivEndCityImage);
        tvStartCity = findViewById(R.id.tvSelectedStartCity);
        tvEndCity = findViewById(R.id.tvSelectedEndCity);
        tvPrice = findViewById(R.id.tvSelectedPrice);
        tvStartDate = findViewById(R.id.tvSelectedStartDate);
        tvEndDate = findViewById(R.id.tvSelectedEndDate);
        ivIcon = findViewById(R.id.ivSelectedIcon);

        Trip trip = (Trip) getIntent().getSerializableExtra("selected_trip");

        tvStartCity.setText("Sale desde: " + trip.getCiudadProcedencia());
        tvEndCity.setText(trip.getCiudadDestino());
        tvStartDate.setText("Fecha de Ida: " + trip.getFechaIda());
        tvEndDate.setText("Fecha de Vuelta: " + trip.getFechaVuelta());
        tvPrice.setText(trip.getPrecio() + "€");

        if(trip.getSeleccionar()) {
            ivIcon.setImageResource(R.drawable.green_tick);
        } else {
            ivIcon.setImageResource(R.drawable.red_cross);
        }

    }

}