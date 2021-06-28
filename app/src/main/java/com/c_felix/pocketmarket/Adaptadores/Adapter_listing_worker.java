package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Desplegar.Listings.Lista_MyListings;
import com.c_felix.pocketmarket.Login;
import com.c_felix.pocketmarket.Menu_Vendedor;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_listing_worker extends RecyclerView.Adapter<Adapter_listing_worker.ViewHolder> {

    JSONArray productos_source, productos_filtrados;
    Context context;
    RecyclerView rvEncabezado;
    TextView txtNoHay;
    private RequestQueue queue;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_worker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try{
            final JSONObject listing = productos_filtrados.getJSONObject(position);
            final JSONObject user = new JSONObject(SQLITE.obtenerUsuarioActivo(context).get(0).getUser());
            JSONObject job = listing.getJSONObject("job");
            queue = Volley.newRequestQueue(context);
            holder.txt_title.setText(listing.getString("name"));
            holder.txt_category.setText(job.getString("name"));
            holder.ivFoto.setBackground(null);
            Picasso.get().load(Uris.IMAGES_ENDPOINT+listing.getString("image")).into(holder.ivFoto);
            holder.txt_description.setText(listing.getString("description"));


            holder.btn_aplicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                    dialogo1.setTitle("Deseas aplicar para este trabajo?");
                    dialogo1.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            try {
                                String url = Uris.API_ENDPOINT + "/accept/listing/"+listing.get("id").toString()+"/"+user.getInt("id");
                                System.out.println(url);
                               StringRequest request = new StringRequest
                                        (Request.Method.PUT, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(context, "Se aplico correctamente", Toast.LENGTH_SHORT).show();
                                                holder.btn_aplicar.setClickable(false);
                                                holder.btn_aplicar.setText("Listo");
                                                holder.btn_aplicar.setBackground(null);
                                                holder.btn_aplicar.setBackgroundColor(12);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                            }
                                        });
                                queue.add(request);

                            } catch (JSONException e) {
                                Log.e("TAG", "onClick: ", e);
                            }
                        }
                    });
                    dialogo1.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.dismiss();
                        }
                    });
                    dialogo1.show();
                }
            });
        }catch (JSONException error){
            Log.e("TAG", "onBindViewHolder: ",error );
        }



    }

    @Override
    public int getItemCount() {
        return productos_filtrados.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title , txt_description, txt_category;
        ImageView ivFoto;
        View divisor;
        Button btn_aplicar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            divisor = itemView.findViewById(R.id.rvDivisor);
            txt_title = itemView.findViewById(R.id.txt_title);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_category = itemView.findViewById(R.id.txt_category);
            btn_aplicar = itemView.findViewById(R.id.btn_aplicar);

        }
    }
}
