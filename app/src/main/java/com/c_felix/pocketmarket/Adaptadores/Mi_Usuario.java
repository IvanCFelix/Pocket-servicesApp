package com.c_felix.pocketmarket.Adaptadores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c_felix.pocketmarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mi_Usuario extends Fragment {
    CircleImageView civPerfil;

    SwipeRefreshLayout swipeRefreshLayout;

    String Usuario;

    TextView txtNombre, txtCorreo;

    public Mi_Usuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_mi__usuario, container, false);
        civPerfil = view.findViewById(R.id.civ_perfil);
        txtCorreo = view.findViewById(R.id.txt_perfil_correo);
        txtNombre = view.findViewById(R.id.txt_perfil_nombre);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                try {
                    PrimeThread p = new PrimeThread(140);
                    p.start();
                } catch (Exception e) {
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        getActivity().setTitle(" " + getString(R.string.mi_usuario));

        return  view;
    }

    class PrimeThread extends Thread {
        long minPrime;

        PrimeThread(long minPrime) {
            this.minPrime = minPrime;
        }

        public void run() {
           // leerUsuario();
        }
    }

    public void leerUsuario(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
