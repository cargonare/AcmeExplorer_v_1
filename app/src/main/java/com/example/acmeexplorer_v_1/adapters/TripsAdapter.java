package com.example.acmeexplorer_v_1.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.acmeexplorer_v_1.R;
import com.example.acmeexplorer_v_1.models.Trip;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    private ArrayList<Trip> initialData;
    private OnTripListener mOnTripListener;

    public TripsAdapter(ArrayList<Trip> initialData, OnTripListener onTripListener) {
        this.initialData = initialData;
        this.mOnTripListener = onTripListener;
    }

    public interface OnTripListener {
        void onTripClick(int position);

        void onSelectTrip(int position);
    }

    @Override
    public int getItemCount() {
        return initialData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.trip_item, viewGroup, false);

        return new ViewHolder(view, mOnTripListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Trip trip = initialData.get(position);

        viewHolder.bindView(position, mOnTripListener);

        Glide.with(viewHolder.itemView.getContext()).load(trip.getUrlImagenes()).into(viewHolder.getImageView());
        viewHolder.getTextViewCities().setText("Ciudad: " + trip.getCiudadDestino());
        viewHolder.getTextViewPrice().setText("Precio: " + String.valueOf(trip.getPrecio()) + "€");
        viewHolder.getTextViewDates().setText("Salida: " + trip.getFechaIda());
        viewHolder.getTextViewDatesBack().setText("Llegada: " + trip.getFechaVuelta());
        viewHolder.getSelectedIcon().setImageResource(trip.getSeleccionar() ? R.drawable.green_tick : R.drawable.red_cross);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView, selectedIcon;
        private final TextView textViewCities, textViewPrice, textViewDates, textViewDatesBack;

        OnTripListener onTripListener;

        public ViewHolder(View view, OnTripListener onTripListener) {
            super(view);

            this.onTripListener = onTripListener;

            imageView = view.findViewById(R.id.trip_image);
            selectedIcon = view.findViewById(R.id.selectedIcon);
            textViewCities = view.findViewById(R.id.trip_cities);
            textViewPrice = view.findViewById(R.id.trip_price);
            textViewDates = view.findViewById(R.id.trip_dates);
            textViewDatesBack = view.findViewById(R.id.trip_dates_back);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTripListener.onTripClick(getAdapterPosition());
        }

        public void bindView(int position, OnTripListener onTripListener) {
            selectedIcon.setOnClickListener(v -> onTripListener.onSelectTrip(getAdapterPosition()));
        }

        private ImageView getImageView() {
            return imageView;
        }

        public ImageView getSelectedIcon() {
            return selectedIcon;
        }

        public TextView getTextViewCities() {
            return textViewCities;
        }

        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public TextView getTextViewDates() {
            return textViewDates;
        }
        public TextView getTextViewDatesBack() {
            return textViewDatesBack;
        }
    }
}
