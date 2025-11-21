package com.example.medita;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class barra_nav extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private ViewPagerAdapter adapter;
    private home homeFragment; // Referencia al Home original

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_nav);

        viewPager = findViewById(R.id.viewPager);
        tabs = findViewById(R.id.tabs);

        String nombreUsuario = getIntent().getStringExtra("usuario");

        // Configurar el adapter del ViewPager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Crear los fragmentos para los tabs
        homeFragment = new home(); // Home original
        logros logrosFragment = new logros();
        Bundle args = new Bundle();
        args.putString("usuario", nombreUsuario);
        homeFragment.setArguments(args);
        logrosFragment.setArguments(args);

        // Agregar fragmentos al adapter
        adapter.addFragment(homeFragment, "Inicio");
        adapter.addFragment(new home(), "Sueño"); // ejemplo de tab extra
        adapter.addFragment(logrosFragment, "Trofeos");
        adapter.addFragment(new home(), "Reportes"); // otro ejemplo

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        // Configurar iconos
        if (tabs.getTabAt(0) != null) tabs.getTabAt(0).setIcon(R.drawable.home);
        if (tabs.getTabAt(1) != null) tabs.getTabAt(1).setIcon(R.drawable.dream);
        if (tabs.getTabAt(2) != null) tabs.getTabAt(2).setIcon(R.drawable.trofeonav);
        if (tabs.getTabAt(3) != null) tabs.getTabAt(3).setIcon(R.drawable.reportes);

        // Listener para que al volver a Home se borre cualquier SesionesFragment
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) { // Home
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_home_container);
                    if (current != null && !(current instanceof home)) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_home_container, homeFragment)
                                .commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    // Método público que Home llama para abrir SesionesFragment
    public void mostrarSesionesFragment(String categoria, String titulo) {
        SesionesFragment fragment = SesionesFragment.newInstance(categoria, titulo);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
