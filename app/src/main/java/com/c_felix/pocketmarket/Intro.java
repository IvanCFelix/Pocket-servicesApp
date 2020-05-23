package com.c_felix.pocketmarket;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.c_felix.pocketmarket.Adaptadores.SlideViewPager;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Intro extends AppCompatActivity {

    ViewPager paginas;
    SlideViewPager adaptador;
    TabLayout tabIndicator;
    FloatingActionButton fabSiguiente, fabAtras;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        List<Fragment> lista = new ArrayList<>();

        tabIndicator = findViewById(R.id.tab_indicator);
        paginas = findViewById(R.id.vpIntro);
        fabSiguiente = findViewById(R.id.fab_siguiente);
        fabAtras = findViewById(R.id.fab_atras);
        adaptador = new SlideViewPager(getSupportFragmentManager(), lista);

        paginas.setAdapter(adaptador);

        tabIndicator.setupWithViewPager(paginas, true);

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paginas.getCurrentItem()==0){
                    paginas.setCurrentItem(1);
                }else{
                    guardar();
                }
            }
        });

        fabAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginas.setCurrentItem(0);
            }
        });

        paginas.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    fabSiguiente.setImageResource((R.drawable.ic_siguiente));
                    fabAtras.setVisibility(View.GONE);
                } else {
                    fabSiguiente.setImageResource((R.drawable.ic_finalizar));
                    fabAtras.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void guardar() {
        SharedPreferences pref = getSharedPreferences("intro", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putBoolean("introvisto",true);
        editor.commit();
        finish();
    }
}
