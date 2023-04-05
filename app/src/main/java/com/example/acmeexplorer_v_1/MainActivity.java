package com.example.acmeexplorer_v_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.acmeexplorer_v_1.adapters.EnlaceAdapter;
import com.example.acmeexplorer_v_1.models.Enlace;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Enlace> enlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        enlace = Enlace.generaEnlace();

        EnlaceAdapter adapter = new EnlaceAdapter(enlace, this);

        ListView lvMainMenu = findViewById(R.id.lvMainMenu);

        lvMainMenu.setAdapter(adapter);
    }
}