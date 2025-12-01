package com.example.medita;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class logros extends Fragment {

    private TextView tvSaludo;
    private TextView valRacha;
    private TextView valTiempo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logros, container, false);

        tvSaludo = view.findViewById(R.id.tvSaludo);
        valRacha = view.findViewById(R.id.val_meditate);
        valTiempo = view.findViewById(R.id.val_meditate_times);

        Bundle bundle = getArguments();
        String nombreUsuario = "Usuario";
        if (bundle != null) {
            String nombreBundle = bundle.getString("usuario");
            if (nombreBundle != null && !nombreBundle.isEmpty()) {
                nombreUsuario = nombreBundle;
            }
        }

        tvSaludo.setText("Estos son tu avances, " + nombreUsuario);

        actualizarLogros();

        return view;
    }

    private void actualizarLogros() {
        Bundle bundle = getArguments();
        long tiempoMinutos = 0;
        int racha = 0;

        if (bundle != null) {
            tiempoMinutos = bundle.getLong("tiempo_total_minutos", 0);
            racha = bundle.getInt("racha_diaria", 0);
        }

        valTiempo.setText(tiempoMinutos + " minutos");

        String nivel;
        if (racha >= 30) {
            nivel = "Oro";
        } else if (racha >= 7) {
            nivel = "Plata";
        } else if (racha >= 3) {
            nivel = "Bronce";
        } else {
            nivel = String.valueOf(racha);
        }

        valRacha.setText(nivel);
    }

}
