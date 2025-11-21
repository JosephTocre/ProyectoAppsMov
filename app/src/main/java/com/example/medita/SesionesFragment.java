package com.example.medita;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SesionesFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";
    private static final String ARG_TITULO = "titulo";

    private String categoria;
    private String titulo;

    public SesionesFragment() {}

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

        TextView tvTituloCategoria = view.findViewById(R.id.tvTituloCategoria);
        if (tvTituloCategoria != null && titulo != null) {
            tvTituloCategoria.setText(titulo);
        }

        return view;
    }
}
