package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;


import java.util.ArrayList;

public class Lista_Seleccionar_Producto_Adaptador extends RecyclerView.Adapter<Lista_Seleccionar_Producto_Adaptador.ViewHolder> {

    ArrayList<Productos> productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;

    public Lista_Seleccionar_Producto_Adaptador(ArrayList<Productos> lista, Context context, RecyclerView rv, TextView txtNoHay) {
        this.productos_source = lista;
        this.productos_filtrados = lista;
        this.context = context;
        rvEncabezado = rv;
        this.txtNoHay = txtNoHay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_productos_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Productos productos = productos_filtrados.get(position);
        if(position==0) holder.divisor.setVisibility(View.GONE);
        holder.txtNombre.setText(productos.getTitulo());
        //holder.ivFoto.setImageBitmap(producto.getFoto());
        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setCancelable(false);
                dialogo1.setMessage(context.getString(R.string.deseas_editar_ingeniero));
                dialogo1.setPositiveButton(context.getString(R.string.editar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                      //  context.startActivity(new Intent(context, Activity_Editar_Ingeniero.class).putExtra("ID", ingeniero.getID()));
                    }
                });
                dialogo1.setNegativeButton(context.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        holder.ivEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setCancelable(false);
                dialogo1.setMessage(context.getString(R.string.deseas_eliminar_ingeniero));
                dialogo1.setPositiveButton(context.getString(R.string.eliminar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                         //   SQLITE.borrarIngeniero(context, ingeniero.getID());

                        ArrayList<Productos> productos = SQLITE.obtenerProductos(context);
                        if(productos.isEmpty()){
                            txtNoHay.setVisibility(View.VISIBLE);
                        }else{
                            txtNoHay.setVisibility(View.GONE);
                        }
                        Lista_Seleccionar_Producto_Adaptador adapter = new Lista_Seleccionar_Producto_Adaptador(productos, context, rvEncabezado, txtNoHay);
                        rvEncabezado.setAdapter(adapter);
                    }
                });
                dialogo1.setNegativeButton(context.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return productos_filtrados.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre;
        LinearLayout layout;
        ImageView ivFoto, ivEditar, ivEliminar, ivModificado;
        View divisor;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            divisor = itemView.findViewById(R.id.rvDivisor);
            txtNombre = itemView.findViewById(R.id.rv_txtNombre);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            ivEditar = itemView.findViewById(R.id.rvEditar);
            ivEliminar = itemView.findViewById(R.id.rvEliminar);
            layout = itemView.findViewById(R.id.rv_lyTitulo);
            ivModificado = itemView.findViewById(R.id.rv_iconomodificado);
        }
    }
}
