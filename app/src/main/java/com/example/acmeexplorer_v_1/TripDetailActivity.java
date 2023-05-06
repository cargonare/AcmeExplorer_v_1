package com.example.acmeexplorer_v_1;

import static com.example.acmeexplorer_v_1.Imports.formatearFecha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acmeexplorer_v_1.models.Trip;

public class TripDetailActivity extends AppCompatActivity {
    private ImageView ivImage, ivIcon;
    private TextView tvStartDate, tvEndDate, tvStartCity, tvEndCity, tvPrice;
    private Button payButton;

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
        tvStartDate.setText("Fecha de Ida: " + formatearFecha(trip.getFechaIda()));
        tvEndDate.setText("Fecha de Vuelta: " + formatearFecha(trip.getFechaVuelta()));
        tvPrice.setText("Precio: " + trip.getPrecio() + "€");

        if(trip.getSeleccionar()) {
            ivIcon.setImageResource(R.drawable.green_tick);
            payButton.setVisibility(View.VISIBLE);
        } else {
            ivIcon.setImageResource(R.drawable.red_cross);
            payButton.setVisibility(View.GONE);
        }

    }

    public void comprarTicket(View view) {
        Toast.makeText(this, "Buy Logic goes here", Toast.LENGTH_SHORT).show();
    }
}