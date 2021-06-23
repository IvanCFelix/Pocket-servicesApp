package com.c_felix.pocketmarket.Desplegar.Pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.c_felix.pocketmarket.Adaptadores.Desplegar_Pedidos_Adaptador;
import com.c_felix.pocketmarket.Agregar.Producto.Formulario_Jobs;
import com.c_felix.pocketmarket.R;

public class Lista_MyListings extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Desplegar_Pedidos_Adaptador adapter;
    TextView txtNoHay;
    Button btnAddListing;


    public Lista_MyListings() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.activity__lista__pedidos ,container, false);
        txtNoHay = view.findViewById(R.id.txtNoHay);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnAddListing = view.findViewById(R.id.btnAddListing);
        llenarLista();
        getActivity().setTitle("Pedidos");

        btnAddListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Formulario_Jobs.class));
            }
        });

        return  view;
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
            llenarLista();
        } catch (Exception e) {
        }
    }

    private void llenarLista() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
             /*   try {
                    carrito = SQLITE.obtenerCarrito(getContext());
                    if(productos.size()==0){
                        productos.add(SQLITE.obtenerProducto(getContext(),carrito.get(0).getID_Producto()));
                    }
                    adapter = new Desplegar_Pedidos_Adaptador(productos, getContext(), recyclerView, txtNoHay);
                    recyclerView.setAdapter(adapter);
                    if (carrito.isEmpty() || carrito == null) {
                        txtNoHay.setVisibility(View.VISIBLE);
                    } else {
                        txtNoHay.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                }*/

            }
        });
    }




}
