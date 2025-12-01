package com.example.medita;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_sesiones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesiones);

        ImageButton btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());

        String categoria = getIntent().getStringExtra("categoria");
        String titulo = getIntent().getStringExtra("titulo");

        int colorFondo = getIntent().getIntExtra("colorFondo", 0);

        View layout = findViewById(R.id.layoutSesiones);
        if (colorFondo != 0 && layout != null) {
            layout.setBackgroundColor(colorFondo);
        }

        TextView tvTitulo = findViewById(R.id.tvTituloCategoria);
        if (titulo != null) {
            tvTitulo.setText(titulo);
        }
    }
}
