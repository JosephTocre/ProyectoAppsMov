package com.example.medita;

import android.content.Context;
import android.content.SharedPreferences;
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

        // Obtener nombre del usuario desde SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs_app", Context.MODE_PRIVATE);
        String nombreUsuario = prefs.getString("nombre_usuario", "Usuario");
        tvSaludo.setText("Hola, " + nombreUsuario);

        // Actualizar logros
        actualizarLogros();

        return view;
    }

    private void actualizarLogros() {
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs_app", getActivity().MODE_PRIVATE);

        // Tiempo total en segundos
        long tiempoSegundos = prefs.getLong("tiempo_total_segundos", 0);
        long tiempoMinutos = tiempoSegundos / 60;
        valTiempo.setText(tiempoMinutos + " minutos");

        // Racha diaria (días consecutivos)
        int racha = prefs.getInt("racha_diaria", 0);

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
