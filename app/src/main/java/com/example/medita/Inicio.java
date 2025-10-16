package com.example.medita;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        TextView tvSaludo = findViewById(R.id.tvSaludo);
        String nombreUsuario = getIntent().getStringExtra("usuario");

        tvSaludo.setText("Hola, " + nombreUsuario);
    }
}
