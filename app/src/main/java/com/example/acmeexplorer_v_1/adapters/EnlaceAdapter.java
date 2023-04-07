package com.example.acmeexplorer_v_1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.example.acmeexplorer_v_1.R;
import com.example.acmeexplorer_v_1.models.Enlace;

import java.util.ArrayList;

public class EnlaceAdapter extends BaseAdapter {
    private ArrayList<Enlace> localDataSet;
    Context context;

    public EnlaceAdapter(ArrayList<Enlace> localDataSet, Context context) {
        this.localDataSet = localDataSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return localDataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return localDataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return localDataSet.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Enlace enlace = localDataSet.get(i);

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.menu_item, viewGroup, false);
        }

        CardView cardView = view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.trip_image);
        TextView textView = view.findViewById(R.id.trip_cities);

        imageView.setImageResource(enlace.getRecursoImageView());
        textView.setText(enlace.getDescripcion());
        cardView.setOnClickListener(view2 -> {
            Intent intent = new Intent(context, enlace.getClase());
            context.startActivity(intent);
        });

        return view;
    }
}
