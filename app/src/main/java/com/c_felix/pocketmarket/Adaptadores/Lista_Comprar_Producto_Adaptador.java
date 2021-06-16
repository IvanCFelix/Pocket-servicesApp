package com.c_felix.pocketmarket.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.Desplegar.Activity_Desplegar_Producto;
import com.c_felix.pocketmarket.Login;
import com.c_felix.pocketmarket.Menu_Vendedor;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

public class Lista_Comprar_Producto_Adaptador extends RecyclerView.Adapter<Lista_Comprar_Producto_Adaptador.ViewHolder> {

    ArrayList<Productos> productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;

    public Lista_Comprar_Producto_Adaptador(ArrayList<Productos> lista, Context context, RecyclerView rv, TextView txtNoHay) {
        this.productos_source = lista;
        this.productos_filtrados = lista;
        this.context = context;
        rvEncabezado = rv;
        this.txtNoHay = txtNoHay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_productos_compra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Productos productos = productos_filtrados.get(position);

        holder.txtNombre.setText(productos.getTitulo());
        holder.ivFoto.setImageBitmap(productos.getImagen());
        holder.txtPrecio.setText("$"+productos.getPrecioUnidad()+"0");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.startActivity(new Intent(context, Activity_Desplegar_Producto.class).putExtra("Producto",productos.getID()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return productos_filtrados.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre,txtPrecio, txtEmpresa;
        FrameLayout layout;
        ImageView ivFoto;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.titulo_Producto);
            txtPrecio = itemView.findViewById(R.id.txt_precio);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            layout = itemView.findViewById(R.id.ly_producto);
            txtEmpresa = itemView.findViewById(R.id.txt_empresa);
        }
    }
}
