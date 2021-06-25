package com.c_felix.pocketmarket.Listas.Listings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Adaptadores.Adapter_listing_worker;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONArray;

import java.util.ArrayList;

public class Job_Listing extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter_listing_worker adapter;
    TextView txtNoHay,total,txt_cantidad,textoTotal;
    private RequestQueue queue;
    public Job_Listing() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_lista_carrito ,container, false);

        txtNoHay = view.findViewById(R.id.txtNoHay);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        queue = Volley.newRequestQueue(getContext());

        llenarLista();
        getActivity().setTitle(" Inicio");

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
             getNewListing();

            }
        });
    }

    private void getNewListing(){
        String url = Uris.API_ENDPOINT+"/listingbyprogress?progress=CREADO";
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            adapter = new Adapter_listing_worker(response, getContext(), recyclerView, txtNoHay);
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

    }
}
