package com.example.medita;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class activity_reproductor_video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_video);

        MaterialButton btnRegresar = findViewById(R.id.btnRegresarVideo);
        btnRegresar.setOnClickListener(v -> finish());

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        String urlVideo = getIntent().getStringExtra("urlVideo");
        String videoId = extractYoutubeId(urlVideo);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }
        });
    }
    private String extractYoutubeId(String url) {
        if (url == null) return null;

        if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }

        if (url.contains("youtube.com/watch?v=")) {
            String[] split = url.split("v=");
            if (split.length > 1) {
                String id = split[1];
                int ampersand = id.indexOf("&");
                if (ampersand != -1) id = id.substring(0, ampersand);
                return id;
            }
        }
        return null;
    }
}
