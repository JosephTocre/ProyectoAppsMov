package com.example.medita;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class activity_reproductor_video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor_video);

        hideSystemBars();

        MaterialButton btnRegresar = findViewById(R.id.btnRegresarVideo);
        btnRegresar.setOnClickListener(v -> finish());

        String titulo = getIntent().getStringExtra("tituloVideo");
        if (titulo != null) {
            findViewById(R.id.tvTituloVideo);
        }

        VideoView videoView = findViewById(R.id.videoView);

        String nombreVideo = getIntent().getStringExtra("nombreVideo");

        int idVideo = getResources().getIdentifier(
                nombreVideo,
                "raw",
                getPackageName()
        );

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + idVideo);
        videoView.setVideoURI(uri);

        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);

        videoView.start();
    }

    private void hideSystemBars() {
        final View decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        });

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
