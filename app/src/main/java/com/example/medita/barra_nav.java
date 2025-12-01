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
    private home homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_nav);

        viewPager = findViewById(R.id.viewPager);
        tabs = findViewById(R.id.tabs);

        String nombreUsuario = getIntent().getStringExtra("usuario");

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        homeFragment = new home();
        dormir dormirFragment = new dormir();
        logros logrosFragment = new logros();
        Bundle args = new Bundle();
        args.putString("usuario", nombreUsuario);
        homeFragment.setArguments(args);
        dormirFragment.setArguments(args);
        logrosFragment.setArguments(args);

        adapter.addFragment(homeFragment, "Inicio");
        adapter.addFragment(dormirFragment, "Sueño");
        adapter.addFragment(logrosFragment, "Trofeos");
        adapter.addFragment(new home(), "Reportes");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        if (tabs.getTabAt(0) != null) tabs.getTabAt(0).setIcon(R.drawable.home);
        if (tabs.getTabAt(1) != null) tabs.getTabAt(1).setIcon(R.drawable.dream);
        if (tabs.getTabAt(2) != null) tabs.getTabAt(2).setIcon(R.drawable.trofeonav);
        if (tabs.getTabAt(3) != null) tabs.getTabAt(3).setIcon(R.drawable.reportes);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
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
}
