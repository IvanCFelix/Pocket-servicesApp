package com.c_felix.pocketmarket.Desplegar.Listings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Adaptadores.Adapter_listing_user;
import com.c_felix.pocketmarket.Adaptadores.Adapter_listing_worker;
import com.c_felix.pocketmarket.Adaptadores.Desplegar_Pedidos_Adaptador;
import com.c_felix.pocketmarket.Agregar.Listing.Formulario_Jobs;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONArray;
import org.json.JSONObject;

public class Lista_MyListings extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter_listing_user adapter;
    TextView txtNoHay;
    Button btnAddListing;
    private RequestQueue queue;

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
        queue = Volley.newRequestQueue(getContext());
        llenarLista();
        getActivity().setTitle(" Mis trabajos");
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
             getMyListing();

            }
        });
    }


    private void getMyListing(){
        try {
            JSONObject users  = new JSONObject(SQLITE.obtenerUsuarioActivo(getContext()).get(0).getUser());
            String url = Uris.API_ENDPOINT+"/user/"+users.get("id");
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                adapter = new Adapter_listing_user(response.getJSONArray("listings"), getContext(), recyclerView, txtNoHay);
                                recyclerView.setAdapter(adapter);
                                if (response.length() == 0) {
                                    txtNoHay.setVisibility(View.VISIBLE);
                                } else {
                                    txtNoHay.setVisibility(View.GONE);
                                }
                            }catch (Exception error){
                                Log.println(1, "Json error ",error.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);

        }catch (Exception e){
            Log.e("TAG", "getMyListing: ",e );
        }
    }



}
