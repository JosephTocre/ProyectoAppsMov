package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;

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

        CardView cardVideo1 = findViewById(R.id.videomedi1);
        CardView cardVideo2 = findViewById(R.id.videomedi2);

        cardVideo1.setOnClickListener(v -> {
            Intent i = new Intent(activity_sesiones.this, activity_reproductor_video.class);
            i.putExtra("tituloVideo", "Meditación 1");
            i.putExtra("nombreVideo", "videomedi1");
            startActivity(i);
        });

        cardVideo2.setOnClickListener(v -> {
            Intent i = new Intent(activity_sesiones.this, activity_reproductor_video.class);
            i.putExtra("tituloVideo", "Meditación 2");
            i.putExtra("nombreVideo", "videomedi2");
            startActivity(i);
        });

        MaterialCardView audio1 = findViewById(R.id.audio1);
        MaterialCardView audio2 = findViewById(R.id.audio2);

        audio1.setOnClickListener(v -> {
            Intent intent = new Intent(activity_sesiones.this, activity_audio_medita.class);
            intent.putExtra("tituloAudio", "Meditación de Respiración");
            intent.putExtra("audioResId", R.raw.audiomedi1);
            startActivity(intent);
        });

        audio2.setOnClickListener(v -> {
            Intent intent = new Intent(activity_sesiones.this, activity_audio_medita.class);
            intent.putExtra("tituloAudio", "Sonidos de Naturaleza");
            intent.putExtra("audioResId", R.raw.audiomedi2);
            startActivity(intent);
        });


    }
}
