package com.example.acmeexplorer_v_1;

import static com.example.acmeexplorer_v_1.Imports.transformarFecha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.acmeexplorer_v_1.adapters.EnlaceAdapter;
import com.example.acmeexplorer_v_1.models.Enlace;
import com.example.acmeexplorer_v_1.models.Trip;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private ArrayList<Enlace> enlace;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        enlace = Enlace.generaEnlace();

        EnlaceAdapter adapter = new EnlaceAdapter(enlace, this);

        ListView lvMainMenu = findViewById(R.id.lvMainMenu);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);

        lvMainMenu.setAdapter(adapter);

        FirestoreService firestoreService = FirestoreService.getServiceInstance();
        firestoreService.saveTrip(new Trip("1", "Valencia", "Toledo", 200.57,transformarFecha("2023-05-10"), transformarFecha("2023-05-10"), false, "https://images.ecestaticos.com/Bow6KmixYLtlqWjT5pocMrJ1nyg=/0x0:2143x1399/1200x1200/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F62d%2F269%2Fbf5%2F62d269bf590833e6db3b99007b2d8a74.jpg"), task -> {
            if (task.isSuccessful()){
                Log.i("epic", "firestore almacenado completado: " + task.getResult().getId());
            } else {
                Log.i("epic", "firestore almacenado fallado");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
        switch(item.getItemId()){
            case R.id.page_profile:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }

        return false;
    }
}