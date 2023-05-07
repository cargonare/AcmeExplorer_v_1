package com.example.acmeexplorer_v_1.models;


import android.content.Intent;

import com.example.acmeexplorer_v_1.FirebaseStorageExample;
import com.example.acmeexplorer_v_1.LoginActivity;
import com.example.acmeexplorer_v_1.MainActivity;
import com.example.acmeexplorer_v_1.R;
import com.example.acmeexplorer_v_1.SelectedTripListActivity;
import com.example.acmeexplorer_v_1.TripListActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Enlace {
    private int recursoImageView;
    private String descripcion;
    private Class clase;

    public Enlace(int recursoImageView, String descripcion, Class clase) {
        this.recursoImageView = recursoImageView;
        this.descripcion = descripcion;
        this.clase = clase;
    }

    public int getRecursoImageView() {
        return recursoImageView;
    }

    public void setRecursoImageView(int recursoImageView) {
        this.recursoImageView = recursoImageView;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Class getClase() {
        return clase;
    }

    public void setClase(Class clase) {
        this.clase = clase;
    }

    public static ArrayList<Enlace> generaEnlace() {
        ArrayList<Enlace> enlace = new ArrayList<>();

        enlace.add(new Enlace(R.drawable.viajar, "Viajes disponibles", TripListActivity.class));
        enlace.add(new Enlace(R.drawable.objetivo, "Viajes seleccionados", SelectedTripListActivity.class));
        enlace.add((new Enlace(R.drawable.foto, "Subir Foto", FirebaseStorageExample.class)));

        return enlace;
    }
}
