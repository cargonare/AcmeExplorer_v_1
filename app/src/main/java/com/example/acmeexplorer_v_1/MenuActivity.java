package com.example.acmeexplorer_v_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.acmeexplorer_v_1.adapters.EnlaceAdapter;
import com.example.acmeexplorer_v_1.models.Enlace;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity{
    private ArrayList<Enlace> enlace;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        enlace = Enlace.generaEnlace();

        EnlaceAdapter adapter = new EnlaceAdapter(enlace, this);

        ListView lvMainMenu = findViewById(R.id.lvMainMenu);
        signOut = findViewById(R.id.btnSignOut);

        lvMainMenu.setAdapter(adapter);


        signOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}