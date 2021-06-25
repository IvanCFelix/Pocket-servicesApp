package com.c_felix.pocketmarket.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.c_felix.pocketmarket.R;
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
            JSONObject job = listing.getJSONObject("job");
//            if(position==0) holder.divisor.setVisibility(View.GONE);
            holder.txt_title.setText(listing.getString("name"));
            holder.txt_category.setText(job.getString("name"));
            holder.ivFoto.setBackground(null);
            Picasso.get().load(Uris.IMAGES_ENDPOINT+listing.getString("image")).into(holder.ivFoto);
            holder.txt_description.setText(listing.getString("description"));
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


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            divisor = itemView.findViewById(R.id.rvDivisor);
            txt_title = itemView.findViewById(R.id.txt_title);
            ivFoto = itemView.findViewById(R.id.rv_ivFoto);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_category = itemView.findViewById(R.id.txt_category);

        }
    }
}
