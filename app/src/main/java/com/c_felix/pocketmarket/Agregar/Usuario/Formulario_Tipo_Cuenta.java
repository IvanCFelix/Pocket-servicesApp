package com.c_felix.pocketmarket.Agregar.Usuario;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Agregar.Listing.Formulario_Jobs;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Formulario_Tipo_Cuenta extends Fragment {
    Spinner spn_tipoCuenta, spn_job;
    ArrayList<String> listaRoles = new ArrayList<>();
    LinearLayout lyDatos;
    TextInputLayout empresa;
    EditText txt_description, txt_phone;
    JSONArray jobListing = new JSONArray();
    private RequestQueue queue ;
    ArrayList<String> data = new ArrayList<>();
    Integer jobId;
    Integer tipoCuenta = 0;

    public void getJobs(){
        try {
            String url = Uris.API_ENDPOINT + "/job";
            JsonArrayRequest request = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            jobListing = response;
                            for (int i = 0; i < jobListing.length() ; i++) {

                                try{
                                    JSONObject listing = jobListing.getJSONObject(i);
                                    data.add(listing.getString("name"));
                                    System.out.println("lsting: "+listing.toString());
                                }catch (JSONException e){
                                    Log.e("TAG", "onCreate: ",e );
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

            queue.add(request);
        } catch (Error error) {
        }
    }
    public Formulario_Tipo_Cuenta() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_formulario__tipo__cuenta, container, false);

        spn_tipoCuenta = view.findViewById(R.id.spn_tipoUsuario);
        lyDatos = view.findViewById(R.id.ly_datos);
        spn_job = view.findViewById(R.id.spn_jobs);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_description = view.findViewById(R.id.txt_description);
        queue = Volley.newRequestQueue(getContext());
        listaRoles.add("Tipo de cuenta..");
        listaRoles.add("Trabajador");
        listaRoles.add("Cliente");

        data.add("Seleccionar especializacion");
        getJobs();

        spn_job.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data));
        spn_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    jobId= i-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spn_tipoCuenta.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, listaRoles));

        spn_tipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCuenta = position;
                System.out.println(tipoCuenta);
                if(spn_tipoCuenta.getSelectedItemPosition()==1){
                    lyDatos.setVisibility(View.VISIBLE);
                }else if(spn_tipoCuenta.getSelectedItemPosition()==2) {
                    lyDatos.setVisibility(View.GONE);
                }else{
                    lyDatos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  view;


    }
}
