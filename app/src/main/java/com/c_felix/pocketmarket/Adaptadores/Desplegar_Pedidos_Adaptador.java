package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c_felix.pocketmarket.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class Desplegar_Pedidos_Adaptador extends RecyclerView.Adapter<Desplegar_Pedidos_Adaptador.ViewHolder> {

    ArrayList<JSONObject> productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;

    public Desplegar_Pedidos_Adaptador(ArrayList<JSONObject> lista, Context context, RecyclerView rv, TextView txtNoHay) {
        this.productos_source = lista;
        this.productos_filtrados = lista;
        this.context = context;
        rvEncabezado = rv;
        this.txtNoHay = txtNoHay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_pedido_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.rv_vizualisar.setOnClickListener(new View.OnClickListener() {
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
        ImageView rv_vizualisar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


        }
    }
}
