package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class home extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvSaludo = view.findViewById(R.id.tvSaludo);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreUsuario = bundle.getString("usuario");
            if (nombreUsuario != null) {
                tvSaludo.setText("Hola, " + nombreUsuario);
            }
        }
        configurarClicks(view);
        return view;
    }

    private void configurarClicks(View view) {

        MaterialCardView cardAntiestres = view.findViewById(R.id.button);
        MaterialCardView cardConcentracion = view.findViewById(R.id.concentracion);
        MaterialCardView cardAnsiedad = view.findViewById(R.id.ansiedad);
        MaterialCardView cardRelajacion = view.findViewById(R.id.breathe);
        MaterialCardView cardIra = view.findViewById(R.id.ira);

        cardAntiestres.setOnClickListener(v -> abrirSesiones(
                "antiestres",
                "Antiestrés",
                cardAntiestres
        ));

        cardConcentracion.setOnClickListener(v -> abrirSesiones(
                "concentracion",
                "Concentración",
                cardConcentracion
        ));

        cardAnsiedad.setOnClickListener(v -> abrirSesiones(
                "ansiedad",
                "Ansiedad",
                cardAnsiedad
        ));

        cardRelajacion.setOnClickListener(v -> abrirSesiones(
                "relajacion",
                "Relajación",
                cardRelajacion
        ));

        cardIra.setOnClickListener(v -> abrirSesiones(
                "ira",
                "Gestión de la Ira",
                cardIra
        ));
    }
    private void abrirSesiones(String categoria, String titulo, MaterialCardView cardView) {

        int colorFondo = 0;
        if (cardView.getBackgroundTintList() != null) {
            colorFondo = cardView.getBackgroundTintList().getDefaultColor();
        }

        Intent intent = new Intent(requireContext(), activity_sesiones.class);
        intent.putExtra("categoria", categoria);
        intent.putExtra("titulo", titulo);
        intent.putExtra("colorFondo", colorFondo);
        startActivity(intent);
    }

}
