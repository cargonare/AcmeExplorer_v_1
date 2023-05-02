package com.example.acmeexplorer_v_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.acmeexplorer_v_1.adapters.EnlaceAdapter;
import com.example.acmeexplorer_v_1.models.Enlace;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
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