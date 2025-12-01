package com.example.medita;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import java.util.Calendar;

public class AppMedita extends Application {

    private static final String PREFS_NAME = "prefs_app";
    private static final String KEY_TIEMPO_TOTAL_SEGUNDOS = "tiempo_total_segundos";
    private static final String KEY_ULTIMO_ACCESO = "ultimo_acceso";
    private static final String KEY_RACHA_DIARIA = "racha_diaria";

    private long tiempoSegundos = 0; // usar long por si pasa mucho tiempo
    private int rachaDiaria = 0;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable contadorRunnable;

    @Override
    public void onCreate() {
        super.onCreate();

        // Cargar datos guardados
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        tiempoSegundos = prefs.getLong(KEY_TIEMPO_TOTAL_SEGUNDOS, 0);
        rachaDiaria = prefs.getInt(KEY_RACHA_DIARIA, 0);

        verificarRachaDiaria();

        iniciarContadorTiempo();
    }

    public void verificarRachaDiaria() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        long ultimoAcceso = prefs.getLong(KEY_ULTIMO_ACCESO, 0);

        Calendar hoy = Calendar.getInstance();
        Calendar ultimo = Calendar.getInstance();
        ultimo.setTimeInMillis(ultimoAcceso);

        int diaHoy = hoy.get(Calendar.DAY_OF_YEAR);
        int diaUltimo = ultimo.get(Calendar.DAY_OF_YEAR);
        int yearHoy = hoy.get(Calendar.YEAR);
        int yearUltimo = ultimo.get(Calendar.YEAR);

        boolean actualizarRacha = false;

        if (ultimoAcceso == 0) {
            // Primera vez que abre la app
            rachaDiaria = 1;
            actualizarRacha = true;
        } else if (yearHoy != yearUltimo || diaHoy - diaUltimo > 1) {
            // Se perdió la racha
            rachaDiaria = 1;
            actualizarRacha = true;
        } else if (diaHoy - diaUltimo == 1) {
            // Incrementa la racha
            rachaDiaria++;
            actualizarRacha = true;
        } // else: mismo día, no incrementa

        // --- Desplazar historial de los últimos 7 días ---
        if (actualizarRacha) {
            SharedPreferences.Editor editor = prefs.edit();
            for (int i = 6; i > 0; i--) {
                editor.putInt("tiempo_dia_" + i, prefs.getInt("tiempo_dia_" + (i - 1), 0));
                editor.putInt("racha_dia_" + i, prefs.getInt("racha_dia_" + (i - 1), 0));
            }
            editor.putInt("tiempo_dia_0", 0); // iniciar tiempo de hoy en 0
            editor.putInt("racha_dia_0", rachaDiaria);
            editor.putInt(KEY_RACHA_DIARIA, rachaDiaria);
            editor.putLong(KEY_ULTIMO_ACCESO, System.currentTimeMillis());
            editor.apply();
        }
    }

    private void iniciarContadorTiempo() {
        contadorRunnable = new Runnable() {
            @Override
            public void run() {
                tiempoSegundos++;
                // Guardar cada 60 segundos
                if (tiempoSegundos % 60 == 0) {
                    guardarTiempo();
                }
                handler.postDelayed(this, 1000); // cada segundo
            }
        };
        handler.post(contadorRunnable);
    }

    private void guardarTiempo() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_TIEMPO_TOTAL_SEGUNDOS, tiempoSegundos);
        // Guardar tiempo de hoy en minutos
        editor.putInt("tiempo_dia_0", (int)(tiempoSegundos / 60));
        editor.apply();
    }

    public long getTiempoMinutos() {
        return tiempoSegundos / 60;
    }

    public int getRachaDiaria() {
        return rachaDiaria;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (contadorRunnable != null) {
            handler.removeCallbacks(contadorRunnable);
        }
        guardarTiempo();
    }
}
