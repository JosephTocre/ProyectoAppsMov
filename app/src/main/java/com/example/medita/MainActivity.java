package com.example.medita;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear canal de notificaciones (solo Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "nirvana_channel"; // ID del canal
            String channelName = "Notificaciones Nirvana";
            String channelDescription = "Recordatorios y promociones de la app Nirvana";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Suscribirse al topic global para recibir notificaciones
        FirebaseMessaging.getInstance().subscribeToTopic("nirvana_global")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Log de prueba, puedes quitarlo luego
                        System.out.println("Suscrito al topic nirvana_global");
                    } else {
                        System.out.println("Fallo al suscribirse al topic");
                    }
                });
    }

    // Método para abrir Login desde el botón
    public void MostrarLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
}
