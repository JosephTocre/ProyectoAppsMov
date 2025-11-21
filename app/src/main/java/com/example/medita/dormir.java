package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.card.MaterialCardView;

public class dormir extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dormir, container, false);

        // Configurar saludo del usuario
        configurarSaludo(view);

        // Configurar los clicks en las cards
        configurarClicks(view);

        return view;
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

        // Noche estrellada
        cardNoche.setOnClickListener(v ->
                navegarAReproductor("Noche estrellada",
                        R.drawable.noche,
                        R.raw.noche_estrellada));

        // Cascada
        cardCascada.setOnClickListener(v ->
                navegarAReproductor("Cascada",
                        R.drawable.cascada,
                        R.raw.cascada));

        // Playa
        cardPlaya.setOnClickListener(v ->
                navegarAReproductor("Playa",
                        R.drawable.playa,
                        R.raw.playa));

        // Paz de sabiduría
        cardPaz.setOnClickListener(v ->
                navegarAReproductor("Paz de sabiduría",
                        R.drawable.paz,
                        R.raw.paz_sabiduria));
    }

    private void navegarAReproductor(String titulo, int imagenRes, int audioRes) {
        Intent intent = new Intent(getActivity(), ReproductorAudioActivity.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("imagen", imagenRes);
        intent.putExtra("audio", audioRes);
        startActivity(intent);
    }
}