package com.example.medita;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class barra_nav extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_nav);

        viewPager = findViewById(R.id.viewPager);
        tabs = findViewById(R.id.tabs);

        String nombreUsuario = getIntent().getStringExtra("usuario");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Pasar el nombre por fragmento
        home homeFragment = new home();
        logros logrosFragment = new logros();
        Bundle args = new Bundle();
        args.putString("usuario", nombreUsuario);
        homeFragment.setArguments(args);
        logrosFragment.setArguments(args);

        adapter.addFragment(homeFragment, "Inicio");
        adapter.addFragment(new home(), "Sueño");
        adapter.addFragment(logrosFragment, "Trofeos");
        adapter.addFragment(new home(), "Reportes");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        if (tabs.getTabAt(0) != null) tabs.getTabAt(0).setIcon(R.drawable.home);
        if (tabs.getTabAt(1) != null) tabs.getTabAt(1).setIcon(R.drawable.dream);
        if (tabs.getTabAt(2) != null) tabs.getTabAt(2).setIcon(R.drawable.trofeonav);
        if (tabs.getTabAt(3) != null) tabs.getTabAt(3).setIcon(R.drawable.reportes);
    }
}
