package com.example.medita;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_audio_medita extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton btnPlayPause;
    private SeekBar seekBar;
    private TextView tvTiempoActual, tvTiempoTotal, tvTituloAudio;
    private ImageView imgFondoAudio;

    private Handler handler = new Handler();
    private Runnable updateSeekBar;

    private int[] fondos = { R.drawable.fondo1, R.drawable.fondo2 };
    private String[] audios = { "audiomedi1", "audiomedi2" };
    private String[] titulos = { "Meditación 1", "Meditación 2" };

    private int audioActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_medita);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBarAudio);
        tvTiempoActual = findViewById(R.id.tvTiempoActual);
        tvTiempoTotal = findViewById(R.id.tvTiempoTotal);
        tvTituloAudio = findViewById(R.id.tvTituloAudio);
        imgFondoAudio = findViewById(R.id.imgFondoAudio);

        findViewById(R.id.btnRegresarAudio).setOnClickListener(v -> finish());

        audioActual = getIntent().getIntExtra("audioIndex", 0);
        reproducirAudio(audioActual);

        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    actualizarSeekBar();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    tvTiempoActual.setText(formatoTiempo(progress));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void reproducirAudio(int index) {
        imgFondoAudio.setImageResource(fondos[index]);
        tvTituloAudio.setText(titulos[index]);

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
        }

        int audioResId = getResources().getIdentifier(audios[index], "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, audioResId);

        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
            tvTiempoTotal.setText(formatoTiempo(mediaPlayer.getDuration()));
            mediaPlayer.start();
            btnPlayPause.setImageResource(R.drawable.ic_pause);
            actualizarSeekBar();
        }
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
