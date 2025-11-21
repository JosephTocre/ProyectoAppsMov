package com.example.medita;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

        cardAntiestres.setOnClickListener(v -> abrirSesiones("antiestres", "Antiestrés"));
        cardConcentracion.setOnClickListener(v -> abrirSesiones("concentracion", "Concentración"));
        cardAnsiedad.setOnClickListener(v -> abrirSesiones("ansiedad", "Ansiedad"));
        cardRelajacion.setOnClickListener(v -> abrirSesiones("relajacion", "Relajación"));
        cardIra.setOnClickListener(v -> abrirSesiones("ira", "Gestión de la Ira"));
    }

    private void abrirSesiones(String categoria, String titulo) {
        SesionesFragment fragment = SesionesFragment.newInstance(categoria, titulo);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        FrameLayout container = getView().findViewById(R.id.fragment_home_container);
        container.removeAllViews();

        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        getChildFragmentManager().popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
