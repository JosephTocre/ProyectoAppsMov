package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.Map;

public class activity_sesiones extends AppCompatActivity {

    static class Contenido {
        String[] videoTitulos;
        String[] videoDescripciones;
        String[] videoDuraciones;
        String[] videoUrls;
        String[] audioTitulos;
        String[] audioUrls;
        String[] audioDuraciones;

        Contenido(String[] vt, String[] vd, String[] vdu, String[] vu,
                  String[] at, String[] au, String[] adu) {
            videoTitulos = vt;
            videoDescripciones = vd;
            videoDuraciones = vdu;
            videoUrls = vu;
            audioTitulos = at;
            audioUrls = au;
            audioDuraciones = adu;
        }
    }

    private Map<String, Contenido> categoriasMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesiones);

        ImageButton btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());

        String categoria = getIntent().getStringExtra("categoria");
        String titulo = getIntent().getStringExtra("titulo");
        int colorFondo = getIntent().getIntExtra("colorFondo", 0);
        View layout = findViewById(R.id.layoutSesiones);
        if (colorFondo != 0 && layout != null) layout.setBackgroundColor(colorFondo);

        TextView tvTitulo = findViewById(R.id.tvTituloCategoria);
        if (titulo != null) tvTitulo.setText(titulo);

        inicializarCategorias();
        Contenido contenido = categoriasMap.getOrDefault(categoria, categoriasMap.get("default"));

        asignarVideo(R.id.videomedi1, R.id.tvTituloVideo1, R.id.tvDescripcionVideo1, R.id.tvDuracionVideo1,
                contenido.videoTitulos[0], contenido.videoDescripciones[0], contenido.videoDuraciones[0], contenido.videoUrls[0]);
        asignarVideo(R.id.videomedi2, R.id.tvTituloVideo2, R.id.tvDescripcionVideo2, R.id.tvDuracionVideo2,
                contenido.videoTitulos[1], contenido.videoDescripciones[1], contenido.videoDuraciones[1], contenido.videoUrls[1]);

        asignarAudio(R.id.audio1, R.id.tvTituloAudio1, R.id.tvDuracionAudio1,
                contenido.audioTitulos[0], 0, contenido.audioDuraciones[0]);
        asignarAudio(R.id.audio2, R.id.tvTituloAudio2, R.id.tvDuracionAudio2,
                contenido.audioTitulos[1], 1, contenido.audioDuraciones[1]);
    }

    private void asignarVideo(int cardId, int tvTituloId, int tvDescripcionId, int tvDuracionId,
                              String titulo, String descripcion, String duracion, String urlVideo) {
        MaterialCardView card = findViewById(cardId);
        TextView tvTitulo = findViewById(tvTituloId);
        TextView tvDescripcion = findViewById(tvDescripcionId);
        TextView tvDuracion = findViewById(tvDuracionId);

        tvTitulo.setText(titulo);
        tvDescripcion.setText(descripcion);
        tvDuracion.setText(duracion);

        card.setOnClickListener(v -> {
            Intent i = new Intent(activity_sesiones.this, activity_reproductor_video.class);
            i.putExtra("tituloVideo", titulo);
            i.putExtra("urlVideo", urlVideo);
            startActivity(i);
        });
    }

    private void asignarAudio(int cardId, int tvTituloId, int tvDuracionId,
                              String titulo, int audioIndex, String duracion) {
        MaterialCardView card = findViewById(cardId);
        TextView tvTitulo = findViewById(tvTituloId);
        TextView tvDuracion = findViewById(tvDuracionId);

        tvTitulo.setText(titulo);
        tvDuracion.setText(duracion);

        card.setOnClickListener(v -> {
            Intent i = new Intent(activity_sesiones.this, activity_audio_medita.class);
            i.putExtra("audioIndex", audioIndex);
            startActivity(i);
        });
    }
    private void inicializarCategorias() {
        categoriasMap.put("antiestres", new Contenido(
                new String[]{"Meditación AntiEstrés 1", "Meditación AntiEstrés 2"},
                new String[]{"Relaja tu mente y cuerpo", "Técnicas para reducir estrés"},
                new String[]{"10 min", "15 min"},
                new String[]{
                        "https://www.youtube.com/watch?v=JnsSiloyFYQ",
                        "https://www.youtube.com/watch?v=RMC5cGccE_4"
                },
                new String[]{"Audio AntiEstrés 1", "Audio AntiEstrés 2"},
                new String[]{
                        "audiomedi1",
                        "audiomedi2"
                },
                new String[]{"8 min", "15 min"}
        ));

        categoriasMap.put("concentracion", new Contenido(
                new String[]{"Concentración 1", "Concentración 2"},
                new String[]{"Ejercicios para enfocarte mejor", "Meditación para aumentar concentración"},
                new String[]{"12 min", "15 min"},
                new String[]{
                        "https://www.youtube.com/watch?v=E8Jr0KoQGW0",
                        "https://www.youtube.com/watch?v=RH_yhzvhp_c"
                },
                new String[]{"Audio Concentración 1", "Audio Concentración 2"},
                new String[]{
                        "audiomedi1",
                        "audiomedi2"
                },
                new String[]{"10 min", "20 min"}
        ));

        categoriasMap.put("ansiedad", new Contenido(
                new String[]{"Ansiedad 1", "Ansiedad 2"},
                new String[]{"Relajación profunda para ansiedad", "Técnicas de respiración contra ansiedad"},
                new String[]{"10 min", "12 min"},
                new String[]{
                        "https://www.youtube.com/Ug4LDCfajeA",
                        "https://www.youtube.com/watch?v=LmUDWMHnPzQ"
                },
                new String[]{"Audio Ansiedad 1", "Audio Ansiedad 2"},
                new String[]{
                        "audiomedi1",
                        "audiomedi2"
                },
                new String[]{"8 min", "15 min"}
        ));

        categoriasMap.put("relajacion", new Contenido(
                new String[]{"Relajación 1", "Relajación 2"},
                new String[]{"Relajación profunda para relajación", "Meditaciones para calmar la mente"},
                new String[]{"10 min", "12 min"},
                new String[]{
                        "https://www.youtube.com/shorts/GyVBS01ares?feature=share",
                        "https://www.youtube.com/watch?v=MkV5ndHffGE"
                },
                new String[]{"Audio Relajación 1", "Audio Relajación 2"},
                new String[]{
                        "audiomedi1",
                        "audiomedi2"
                },
                new String[]{"8 min", "15 min"}
        ));

        categoriasMap.put("ira", new Contenido(
                new String[]{"Control de Ira 1", "Control de Ira 2"},
                new String[]{"Aprende a calmarte", "Técnicas de meditación para la ira"},
                new String[]{"10 min", "15 min"},
                new String[]{
                        "https://www.youtube.com/watch?v=8b6ZuX_WMMo",
                        "https://www.youtube.com/watch?v=RCGmD1Fwzd8"
                },
                new String[]{"Audio Ira 1", "Audio Ira 2"},
                new String[]{
                        "audiomedi1",
                        "audiomedi2"
                },
                new String[]{"10 min", "20 min"}
        ));
    }
}
