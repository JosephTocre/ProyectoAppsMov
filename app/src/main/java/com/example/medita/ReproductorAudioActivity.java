package com.example.medita;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import java.util.Locale;

public class ReproductorAudioActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;

    // Views
    private ImageView imgFondo;
    private TextView tvTituloMeditacion;
    private MaterialButton btnRegresar;
    private MaterialButton btnPlayPause;
    private MaterialButton btnRetroceder;
    private MaterialButton btnAdelantar;
    private Slider sliderProgreso;
    private TextView tvTiempoActual;
    private TextView tvDuracionTotal;

    // Datos del audio
    private String titulo;
    private int imagenRes;
    private int audioRes;

    private Runnable actualizarProgreso = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && isPlaying) {
                actualizarProgreso();
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_audio);

        // Obtener datos del intent
        titulo = getIntent().getStringExtra("titulo");
        imagenRes = getIntent().getIntExtra("imagen", R.drawable.noche);
        audioRes = getIntent().getIntExtra("audio", 0);

        inicializarViews();
        configurarUI();
        configurarClicks();
        inicializarMediaPlayer();
    }

    private void inicializarViews() {
        imgFondo = findViewById(R.id.imgFondo);
        tvTituloMeditacion = findViewById(R.id.tvTituloMeditacion);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnRetroceder = findViewById(R.id.btnRetroceder);
        btnAdelantar = findViewById(R.id.btnAdelantar);
        sliderProgreso = findViewById(R.id.sliderProgreso);
        tvTiempoActual = findViewById(R.id.tvTiempoActual);
        tvDuracionTotal = findViewById(R.id.tvDuracionTotal);
    }

    private void configurarUI() {
        tvTituloMeditacion.setText(titulo);
        imgFondo.setImageResource(imagenRes);
    }

    private void configurarClicks() {
        btnRegresar.setOnClickListener(v -> finish());

        btnPlayPause.setOnClickListener(v -> togglePlayPause());

        btnRetroceder.setOnClickListener(v -> retroceder10Segundos());

        btnAdelantar.setOnClickListener(v -> adelantar10Segundos());

        sliderProgreso.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser && mediaPlayer != null) {
                int nuevaPosicion = (int) (value * mediaPlayer.getDuration() / 100);
                mediaPlayer.seekTo(nuevaPosicion);
            }
        });
    }

    private void inicializarMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(this, audioRes);

            if (mediaPlayer != null) {
                configurarMediaPlayer();
            } else {
                mostrarErrorAudio();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErrorAudio();
        }
    }

    private void configurarMediaPlayer() {
        mediaPlayer.setOnPreparedListener(mp -> {
            int duracionTotal = mediaPlayer.getDuration();
            tvDuracionTotal.setText(formatearTiempo(duracionTotal));
            sliderProgreso.setValueTo(100);
            iniciarActualizacionProgreso();
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            isPlaying = false;
            actualizarBotonPlayPause();
            handler.removeCallbacks(actualizarProgreso);
        });
    }

    private void togglePlayPause() {
        if (mediaPlayer == null) return;

        if (isPlaying) {
            pausarAudio();
        } else {
            reproducirAudio();
        }
    }

    private void reproducirAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
            actualizarBotonPlayPause();
            iniciarActualizacionProgreso();
        }
    }

    private void pausarAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            isPlaying = false;
            actualizarBotonPlayPause();
            handler.removeCallbacks(actualizarProgreso);
        }
    }

    private void retroceder10Segundos() {
        if (mediaPlayer != null) {
            int posicionActual = mediaPlayer.getCurrentPosition();
            int nuevaPosicion = Math.max(0, posicionActual - 10000);
            mediaPlayer.seekTo(nuevaPosicion);
            actualizarProgreso();
        }
    }

    private void adelantar10Segundos() {
        if (mediaPlayer != null) {
            int posicionActual = mediaPlayer.getCurrentPosition();
            int duracionTotal = mediaPlayer.getDuration();
            int nuevaPosicion = Math.min(duracionTotal, posicionActual + 10000);
            mediaPlayer.seekTo(nuevaPosicion);
            actualizarProgreso();
        }
    }

    private void actualizarBotonPlayPause() {
        if (isPlaying) {
            btnPlayPause.setIconResource(R.drawable.ic_pause);
        } else {
            btnPlayPause.setIconResource(R.drawable.ic_play);
        }
    }

    private void actualizarProgreso() {
        if (mediaPlayer != null) {
            int posicionActual = mediaPlayer.getCurrentPosition();
            int duracionTotal = mediaPlayer.getDuration();

            tvTiempoActual.setText(formatearTiempo(posicionActual));

            if (duracionTotal > 0) {
                int progreso = (int) ((posicionActual * 100.0) / duracionTotal);
                sliderProgreso.setValue(progreso);
            }
        }
    }

    private void iniciarActualizacionProgreso() {
        handler.removeCallbacks(actualizarProgreso);
        handler.postDelayed(actualizarProgreso, 1000);
    }

    private String formatearTiempo(int milisegundos) {
        int segundos = milisegundos / 1000;
        int minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
    }

    private void mostrarErrorAudio() {
        tvTituloMeditacion.setText("Error cargando audio");
        btnPlayPause.setEnabled(false);
        btnRetroceder.setEnabled(false);
        btnAdelantar.setEnabled(false);
    }

    private void detenerReproduccion() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(actualizarProgreso);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && isPlaying) {
            pausarAudio();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detenerReproduccion();
    }
}