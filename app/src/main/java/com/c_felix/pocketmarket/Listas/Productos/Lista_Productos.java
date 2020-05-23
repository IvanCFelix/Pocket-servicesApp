package com.c_felix.pocketmarket.Listas.Productos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c_felix.pocketmarket.Adaptadores.Lista_Seleccionar_Producto_Adaptador;
import com.c_felix.pocketmarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
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

     return view;
    }
}
