package com.example.medita;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class dormir extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dormir, container, false);

        configurarSaludo(rootView);
        configurarClicks(rootView);
        configurarSensor();

        return rootView;
    }

    private void configurarSaludo(View view) {
        TextView tvSaludo = view.findViewById(R.id.tvSaludo);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreUsuario = bundle.getString("usuario");
            if (nombreUsuario != null) {
                tvSaludo.setText("A dormir, " + nombreUsuario);
            }
        }
    }

    private void configurarClicks(View view) {
        MaterialCardView cardNoche = view.findViewById(R.id.cardNoche);
        MaterialCardView cardCascada = view.findViewById(R.id.cardCascada);
        MaterialCardView cardPlaya = view.findViewById(R.id.cardPlaya);
        MaterialCardView cardPaz = view.findViewById(R.id.cardPaz);

        cardNoche.setOnClickListener(v ->
                navegarAReproductor("Noche estrellada", R.drawable.noche, R.raw.noche_estrellada));

        cardCascada.setOnClickListener(v ->
                navegarAReproductor("Cascada", R.drawable.cascada, R.raw.cascada));

        cardPlaya.setOnClickListener(v ->
                navegarAReproductor("Playa", R.drawable.playa, R.raw.playa));

        cardPaz.setOnClickListener(v ->
                navegarAReproductor("Paz de sabiduría", R.drawable.paz, R.raw.paz_sabiduria));
    }

    private void navegarAReproductor(String titulo, int imagenRes, int audioRes) {
        Intent intent = new Intent(getActivity(), ReproductorAudioActivity.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("imagen", imagenRes);
        intent.putExtra("audio", audioRes);
        startActivity(intent);
    }

    private void configurarSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            if (proximitySensor != null) {
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            if (distance < proximitySensor.getMaximumRange()) {
                rootView.setBackgroundColor(Color.parseColor("#40514E"));
            } else {
                rootView.setBackgroundColor(Color.parseColor("#C0D9D8"));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
