package com.example.medita;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SesionesFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";
    private static final String ARG_TITULO = "titulo";

    private String categoria;
    private String titulo;

    public SesionesFragment() {
        // Required empty public constructor
    }

    /**
     * Método para crear nueva instancia con la categoría
     */
    public static SesionesFragment newInstance(String categoria, String titulo) {
        SesionesFragment fragment = new SesionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORIA, categoria);
        args.putString(ARG_TITULO, titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoria = getArguments().getString(ARG_CATEGORIA);
            titulo = getArguments().getString(ARG_TITULO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sesiones, container, false);

        // Configurar la UI con los datos
        configurarUI(view);

        return view;
    }

    private void configurarUI(View view) {
        // Configurar título de la categoría
        TextView tvTituloCategoria = view.findViewById(R.id.tvTituloCategoria);
        if (titulo != null) {
            tvTituloCategoria.setText(titulo);
        }

        // Configurar contenido según la categoría
        configurarContenidoPorCategoria(view);

        // Configurar clicks en los videos y audios
        configurarClicks(view);
    }

    private void configurarContenidoPorCategoria(View view) {
        TextView tvTituloVideo1 = view.findViewById(R.id.tvTituloVideo1);
        TextView tvTituloVideo2 = view.findViewById(R.id.tvTituloVideo2);
        TextView tvTituloAudio1 = view.findViewById(R.id.tvTituloAudio1);
        TextView tvTituloAudio2 = view.findViewById(R.id.tvTituloAudio2);

        if (categoria != null) {
            switch (categoria) {
                case "antiestres":
                    tvTituloVideo1.setText("Meditación para aliviar estrés");
                    tvTituloVideo2.setText("Respiración consciente");
                    tvTituloAudio1.setText("Sonidos relajantes para el estrés");
                    tvTituloAudio2.setText("Meditación guiada antiestrés");
                    break;

                case "concentracion":
                    tvTituloVideo1.setText("Meditación para enfoque mental");
                    tvTituloVideo2.setText("Ejercicios de atención plena");
                    tvTituloAudio1.setText("Sonidos para concentración");
                    tvTituloAudio2.setText("Meditación para claridad mental");
                    break;

                case "ansiedad":
                    tvTituloVideo1.setText("Calmando la ansiedad");
                    tvTituloVideo2.setText("Técnicas de grounding");
                    tvTituloAudio1.setText("Respiración para ansiedad");
                    tvTituloAudio2.setText("Sonidos calmantes");
                    break;

                case "relajacion":
                    tvTituloVideo1.setText("Relajación profunda");
                    tvTituloVideo2.setText("Yoga suave para relajar");
                    tvTituloAudio1.setText("Música relajante");
                    tvTituloAudio2.setText("Meditación del cuerpo scan");
                    break;

                case "ira":
                    tvTituloVideo1.setText("Manejando emociones fuertes");
                    tvTituloVideo2.setText("Meditación de la compasión");
                    tvTituloAudio1.setText("Respiración para calmar la ira");
                    tvTituloAudio2.setText("Sonidos para equilibrio emocional");
                    break;

                default:
                    tvTituloVideo1.setText("Meditación guiada");
                    tvTituloVideo2.setText("Práctica de mindfulness");
                    tvTituloAudio1.setText("Audio de meditación");
                    tvTituloAudio2.setText("Sonidos relajantes");
                    break;
            }
        }
    }

    private void configurarClicks(View view) {
        // Configurar clicks en los cards de video
        View cardVideo1 = view.findViewById(R.id.containerVideos).getChildAt(0);
        View cardVideo2 = view.findViewById(R.id.containerVideos).getChildAt(1);

        // Configurar clicks en los cards de audio
        View cardAudio1 = view.findViewById(R.id.containerAudios).getChildAt(0);
        View cardAudio2 = view.findViewById(R.id.containerAudios).getChildAt(1);

        cardVideo1.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reproduciendo video 1", Toast.LENGTH_SHORT).show();
            // Aquí iría la lógica para reproducir video
        });

        cardVideo2.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reproduciendo video 2", Toast.LENGTH_SHORT).show();
        });

        cardAudio1.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reproduciendo audio 1", Toast.LENGTH_SHORT).show();
            // Aquí iría la lógica para reproducir audio
        });

        cardAudio2.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reproduciendo audio 2", Toast.LENGTH_SHORT).show();
        });
    }
}