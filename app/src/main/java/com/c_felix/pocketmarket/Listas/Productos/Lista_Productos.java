package com.c_felix.pocketmarket.Listas.Productos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c_felix.pocketmarket.Adaptadores.Lista_Seleccionar_Producto_Adaptador;
import com.c_felix.pocketmarket.Agregar.Producto.Formulario_Jobs;
import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

public class Lista_Productos extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Lista_Seleccionar_Producto_Adaptador adapter;
    FloatingActionButton fabAgregar;
    ProgressDialog iniciando;
    TextView txtNoHay;
    public Lista_Productos() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_lista__productos, container, false);

        txtNoHay = view.findViewById(R.id.txtNoHay);
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAgregar = view.findViewById(R.id.fab_agregar);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PrimeThread p = new PrimeThread(140);
        p.start();




        getActivity().setTitle(" Productos");

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Formulario_Jobs.class));
            }
        });
        return view;
    }

    class PrimeThread extends Thread {
        long minPrime;

        PrimeThread(long minPrime) {
            this.minPrime = minPrime;
        }

        public void run() {
            llenarLista();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            PrimeThread p = new PrimeThread(140);
            p.start();
            llenarLista();
        } catch (Exception e) {
        }
    }

    private void llenarLista() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
           /*     try {
                    ArrayList<UsuarioActivo> usuarioActivo = SQLITE.obtenerUsuarioActivo(getContext());

                    adapter = new Lista_Seleccionar_Producto_Adaptador(productos, getContext(), recyclerView, txtNoHay);
                    recyclerView.setAdapter(adapter);
                    if (productos.isEmpty() || productos == null) {
                        txtNoHay.setVisibility(View.VISIBLE);
                    } else {
                        txtNoHay.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e+"", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }
}
