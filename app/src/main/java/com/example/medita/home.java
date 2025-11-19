package com.example.medita;

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

        // Configurar los clicks en las cards
        configurarClicks(view);

        return view;
    }

    private void configurarClicks(View view) {
        MaterialCardView cardAntiestres = view.findViewById(R.id.button);
        MaterialCardView cardConcentracion = view.findViewById(R.id.concentracion);
        MaterialCardView cardAnsiedad = view.findViewById(R.id.ansiedad);
        MaterialCardView cardRelajacion = view.findViewById(R.id.breathe);
        MaterialCardView cardIra = view.findViewById(R.id.ira);

        cardAntiestres.setOnClickListener(v -> navegarASesiones("antiestres", "Antiestrés"));
        cardConcentracion.setOnClickListener(v -> navegarASesiones("concentracion", "Concentración"));
        cardAnsiedad.setOnClickListener(v -> navegarASesiones("ansiedad", "Ansiedad"));
        cardRelajacion.setOnClickListener(v -> navegarASesiones("relajacion", "Relajación"));
        cardIra.setOnClickListener(v -> navegarASesiones("ira", "Gestión de la Ira"));
    }

    private void navegarASesiones(String categoria, String titulo) {
        SesionesFragment sesionesFragment = SesionesFragment.newInstance(categoria, titulo);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, sesionesFragment) // Ajusta este ID según tu Activity principal
                .addToBackStack("home") // Permite volver atrás
                .commit();
    }
}