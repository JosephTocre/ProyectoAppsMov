package com.example.medita;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class logros extends Fragment {

    private TextView tvSaludo;
    private TextView valRacha;
    private TextView valTiempo;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable actualizarRunnable;

    private static final String PREFS_NAME = "prefs_app";
    private static final String KEY_ULTIMO_ACCESO = "ultimo_acceso";
    private static final String KEY_RACHA_DIARIA = "racha_diaria";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logros, container, false);

        tvSaludo = view.findViewById(R.id.tvSaludo);
        valRacha = view.findViewById(R.id.val_meditate);
        valTiempo = view.findViewById(R.id.val_meditate_times);

        String nombreUsuario = "Usuario";
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreBundle = bundle.getString("usuario");
            if (nombreBundle != null && !nombreBundle.isEmpty()) {
                nombreUsuario = nombreBundle;
            }
        }

        tvSaludo.setText("Estos son tus avances, " + nombreUsuario);

        iniciarActualizacionEnTiempoReal();

        return view;
    }

    private void iniciarActualizacionEnTiempoReal() {
        actualizarRunnable = new Runnable() {
            @Override
            public void run() {
                verificarNuevoDia();
                actualizarLogros();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(actualizarRunnable);
    }

    private void actualizarLogros() {
        AppMedita app = (AppMedita) requireActivity().getApplication();

        long tiempoMinutos = app.getTiempoMinutos();
        int racha = app.getRachaDiaria();

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

    private void verificarNuevoDia() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        long ultimoAcceso = prefs.getLong(KEY_ULTIMO_ACCESO, 0);

        Calendar hoy = Calendar.getInstance();
        Calendar ultimo = Calendar.getInstance();
        ultimo.setTimeInMillis(ultimoAcceso);

        int diaHoy = hoy.get(Calendar.DAY_OF_YEAR);
        int diaUltimo = ultimo.get(Calendar.DAY_OF_YEAR);
        int yearHoy = hoy.get(Calendar.YEAR);
        int yearUltimo = ultimo.get(Calendar.YEAR);

        if (ultimoAcceso == 0 || yearHoy != yearUltimo || diaHoy - diaUltimo > 1) {
            int nuevaRacha = 1;

            SharedPreferences.Editor editor = prefs.edit();
            for (int i = 6; i > 0; i--) {
                editor.putInt("tiempo_dia_" + i, prefs.getInt("tiempo_dia_" + (i - 1), 0));
                editor.putInt("racha_dia_" + i, prefs.getInt("racha_dia_" + (i - 1), 0));
            }
            editor.putInt("tiempo_dia_0", 0);
            editor.putInt("racha_dia_0", nuevaRacha);
            editor.putInt(KEY_RACHA_DIARIA, nuevaRacha);
            editor.putLong(KEY_ULTIMO_ACCESO, System.currentTimeMillis());
            editor.apply();

            AppMedita app = (AppMedita) requireActivity().getApplication();
            app.verificarRachaDiaria();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (actualizarRunnable != null) {
            handler.removeCallbacks(actualizarRunnable);
        }
    }
}