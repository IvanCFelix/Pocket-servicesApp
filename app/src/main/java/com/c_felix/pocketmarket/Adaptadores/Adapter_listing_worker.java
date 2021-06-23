package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.c_felix.pocketmarket.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_listing_worker extends RecyclerView.Adapter<Adapter_listing_worker.ViewHolder> {

    JSONArray productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;
    public Adapter_listing_worker(JSONArray lista, Context context, RecyclerView rv, TextView txtNoHay) {
        this.productos_source = lista;
        this.productos_filtrados = lista;
        this.context = context;
        rvEncabezado = rv;
        this.txtNoHay = txtNoHay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carrito_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try{
            final JSONObject productos = productos_filtrados.getJSONObject(position);
            JSONObject producto1 = new JSONObject();

        }catch (JSONException error){
        }

        if(position==0) holder.divisor.setVisibility(View.GONE);
        //holder.txtNombre.setText("Nambe");
        //holder.ivFoto.setImageBitmap(productos.getImagen());
        //holder.txt_Precio.setText("Precio: $"+productos.getPrecioUnidad());
      /*  holder.rv_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setCancelable(false);
                dialogo1.setMessage("¿sDesea eliminar del carrito?");
                dialogo1.setPositiveButton("Desea eliminar del carrito?", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        SQLITE.borrarCarrito(context,carrito.get(0).getID());
                        final ArrayList<Carrito> carrito = SQLITE.obtenerCarrito(context);
                        ArrayList<Productos> productosNuevo = new ArrayList<>();
                        if(carrito.size()!=0){
                            productosNuevo.add(SQLITE.obtenerProducto(context,carrito.get(0).getID()));
                        }
                        Lista_Carrito_Producto_Adapter adapter = new Lista_Carrito_Producto_Adapter(productosNuevo, context, rvEncabezado, txtNoHay);
                        rvEncabezado.setAdapter(adapter);
                    }
                });
                dialogo1.setNegativeButton(context.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return productos_filtrados.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre,txt_Precio,txt_total;
        ImageView ivFoto;
        View divisor;
        Spinner contador;
        ImageView rv_borrar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            divisor = itemView.findViewById(R.id.rvDivisor);
            txtNombre = itemView.findViewById(R.id.rv_txtNombre);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            txt_Precio = itemView.findViewById(R.id.txt_precio_carrito);
            contador = itemView.findViewById(R.id.spn_cantidad);
            rv_borrar = itemView.findViewById(R.id.rvEliminar);
        }
    }
}
