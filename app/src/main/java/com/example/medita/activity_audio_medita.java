package com.example.medita;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_audio_medita extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton btnPlayPause;
    private SeekBar seekBar;
    private TextView tvTiempoActual, tvTiempoTotal, tvTituloAudio;

    private Handler handler = new Handler();
    private Runnable updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_medita);

        // Referencias UI
        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBarAudio);
        tvTiempoActual = findViewById(R.id.tvTiempoActual);
        tvTiempoTotal = findViewById(R.id.tvTiempoTotal);
        tvTituloAudio = findViewById(R.id.tvTituloAudio);

        // Recibir datos
        int audioResId = getIntent().getIntExtra("audioResId", -1);
        String titulo = getIntent().getStringExtra("tituloAudio");

        if (titulo != null) {
            tvTituloAudio.setText(titulo);
        }

        // Inicializar MediaPlayer
        mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.setOnPreparedListener(mp -> {
            seekBar.setMax(mp.getDuration());
            tvTiempoTotal.setText(formatoTiempo(mp.getDuration()));
        });

        // Botón Play/Pause
        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
                actualizarSeekBar();
            }
        });

        // Mover SeekBar manualmente
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    tvTiempoActual.setText(formatoTiempo(progress));
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Botón regresar
        findViewById(R.id.btnRegresarAudio).setOnClickListener(v -> finish());
    }

    private void actualizarSeekBar() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int pos = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(pos);
                    tvTiempoActual.setText(formatoTiempo(pos));
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.post(updateSeekBar);
    }

    private String formatoTiempo(int ms) {
        int segundos = ms / 1000;
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
