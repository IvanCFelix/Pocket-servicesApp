package com.c_felix.pocketmarket.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Desplegar.Listings.Show_list_request;
import com.c_felix.pocketmarket.Login;
import com.c_felix.pocketmarket.Menu_Usuario;
import com.c_felix.pocketmarket.Menu_Vendedor;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_listing_user extends RecyclerView.Adapter<Adapter_listing_user.ViewHolder> {

    JSONArray productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;
    public Adapter_listing_user(JSONArray lista, Context context, RecyclerView rv, TextView txtNoHay) {
        this.productos_source = lista;
        this.productos_filtrados = lista;
        this.context = context;
        rvEncabezado = rv;
        this.txtNoHay = txtNoHay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try{
            final JSONObject listing = productos_filtrados.getJSONObject(position);
                JSONObject job = listing.getJSONObject("job");
            holder.txt_title.setText(listing.getString("name"));
            holder.txt_category.setText(job.getString("name"));
            Picasso.get().load(Uris.IMAGES_ENDPOINT+listing.getString("image")).into(holder.ivFoto);
            holder.txt_description.setText(listing.getString("description"));
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Deseas aplicar para este trabajo?")
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    builder.create();
                }
            });

            holder.btn_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        context.startActivity(new Intent(context, Show_list_request.class).putExtra("id_listing",listing.getInt("id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (JSONException error){
        }



    }

    @Override
    public int getItemCount() {
        return productos_filtrados.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView btn_delete;
        TextView txt_title , txt_description, txt_category;
        ImageView ivFoto;
        Button btn_request;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_category = itemView.findViewById(R.id.txt_category);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_request = itemView.findViewById(R.id.btn_solicitudes);
        }
    }
}
