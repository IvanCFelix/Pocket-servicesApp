package com.c_felix.pocketmarket.Listas.Carrito;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.c_felix.pocketmarket.Adaptadores.Desplegar_Pedidos_Adaptador;
import com.c_felix.pocketmarket.Adaptadores.Lista_Carrito_Producto_Adapter;
import com.c_felix.pocketmarket.Clases.Carrito;
import com.c_felix.pocketmarket.Clases.Pedidos;
import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Menu_Vendedor;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

public class Lista_Carrito extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Lista_Carrito_Producto_Adapter adapter;
    TextView txtNoHay,total,txt_cantidad,textoTotal;
    Button ordenar;

    ArrayList<Carrito> carrito = new ArrayList<>();
    ArrayList<Productos> productos = new ArrayList<>();

    public Lista_Carrito() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_lista_carrito ,container, false);

        txtNoHay = view.findViewById(R.id.txtNoHay);
        total = view.findViewById(R.id.txt_total);
        ordenar = view.findViewById(R.id.btn_Ordenar);
        txt_cantidad = view.findViewById(R.id.txt_cantidad);
        textoTotal = view.findViewById(R.id.textoplano_total);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        getActivity().setTitle(" Carrito");
        calcularTotal();


        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedidos pedido = new Pedidos();
                pedido.setID_Producto(productos.get(0).getID());
                pedido.setID_Usuario(carrito.get(0).getID_Usuario());
                pedido.setCantidad(carrito.get(0).getCantidad());
                pedido.setA_Pagar(Double.parseDouble(total.getText().toString()));
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setCancelable(false);
                dialogo1.setMessage("Producto registrado exitosamente");
                dialogo1.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        startActivity(new Intent(getContext(), Menu_Vendedor.class));
                    }
                });
                dialogo1.show();
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
            llenarLista();
        } catch (Exception e) {
        }
    }

    private void llenarLista() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
              /*  try {
                    carrito = SQLITE.obtenerCarrito(getContext());
                    if(productos.size()==0){
                        productos.add(SQLITE.obtenerProducto(getContext(),carrito.get(0).getID_Producto()));
                    }
                    adapter = new Lista_Carrito_Producto_Adapter(productos, getContext(), recyclerView, txtNoHay);
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

    public void calcularTotal(){
        if(carrito.size()!=0){
            ArrayList<Productos> productos = new ArrayList<>();
            int cantidad = carrito.get(0).getCantidad();
            double precio= productos.get(0).getPrecioUnidad();
            total.setText(String.valueOf(cantidad*precio));

        }else{
            total.setVisibility(View.GONE);
            ordenar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Carrito Vacio", Toast.LENGTH_SHORT).show();
            textoTotal.setVisibility(View.GONE);
        }
    }
}
