package com.example.medita;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class fragment_reports extends Fragment {

    private BarChart chartTiempo;
    private LinearLayout rachaContainer;
    private static final String PREFS_NAME = "prefs_app";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        chartTiempo = view.findViewById(R.id.chartTiempo);
        rachaContainer = view.findViewById(R.id.rachaContainer);

        cargarGraficos();

        return view;
    }

    private void cargarGraficos() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);

        // --- Gráfico de tiempo ---
        String[] dias = {"Hoy", "Martes", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        List<BarEntry> entriesTiempo = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int tiempo = prefs.getInt("tiempo_dia_" + i, 0);
            entriesTiempo.add(new BarEntry(i, tiempo));
        }

        BarDataSet dataSetTiempo = new BarDataSet(entriesTiempo, "Tiempo en minutos");
        dataSetTiempo.setColor(ContextCompat.getColor(requireContext(), R.color.teal_700));
        BarData barDataTiempo = new BarData(dataSetTiempo);
        chartTiempo.setData(barDataTiempo);
        chartTiempo.getDescription().setEnabled(false);
        chartTiempo.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dias));
        chartTiempo.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartTiempo.getXAxis().setGranularity(1f);
        chartTiempo.invalidate();

        // --- Racha diaria como rectángulos con nombre de los días ---
        rachaContainer.removeAllViews();

        AppMedita app = (AppMedita) requireActivity().getApplication();
        int rachaHoy = app.getRachaDiaria();

        for (int i = 0; i < 7; i++) {
            int rachaDia = (i == 0) ? rachaHoy : prefs.getInt("racha_dia_" + i, 0);

            TextView dayView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            params.setMargins(8, 0, 8, 0);
            dayView.setLayoutParams(params);

            dayView.setGravity(Gravity.CENTER);
            dayView.setText(dias[i]);
            dayView.setTextColor(Color.WHITE);
            dayView.setTextSize(14f);

            // Color según racha
            int colorFondo = rachaDia > 0
                    ? ContextCompat.getColor(getContext(), R.color.purple_500)
                    : ContextCompat.getColor(getContext(), R.color.gray);
            dayView.setBackgroundColor(colorFondo);

            // Animación de scale
            ScaleAnimation anim = new ScaleAnimation(
                    0f, 1f, 0f, 1f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            );
            anim.setDuration(400);
            dayView.startAnimation(anim);

            rachaContainer.addView(dayView);
        }
    }
}
