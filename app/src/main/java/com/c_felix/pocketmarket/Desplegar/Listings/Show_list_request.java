package com.c_felix.pocketmarket.Desplegar.Listings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Desplegar.Listings.Profile.Show_profile_worker;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_list_request extends AppCompatActivity {

    TextView txtNoHay, txt_name, txt_job;
    Integer id_listing;
    CardView card_worker;
    ImageView img_profile, btn_showProfile;
    private RequestQueue queue;
    Integer id_worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requests);
        txtNoHay = findViewById(R.id.txtNoHay);
        card_worker = findViewById(R.id.card_worker);
        id_listing = getIntent().getIntExtra("id_listing", 0);
        txt_name = findViewById(R.id.rv_txtNombre);
        txt_job = findViewById(R.id.txt_job);
        img_profile = findViewById(R.id.rv_ivFoto);
        btn_showProfile = findViewById(R.id.btn_showProfile);
        queue = Volley.newRequestQueue(Show_list_request.this);
        setTitle("Solicitudes");
        llenarLista();


            btn_showProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Show_list_request.this, Show_profile_worker.class).putExtra("id_worker",id_worker));
                }
            });

    }

    private void llenarLista() {
        runOnUiThread(new Runnable() {
            public void run() {
                getRequest();

            }
        });
    }


    private void getRequest() {
        try {
            String url = Uris.API_ENDPOINT + "/listing/" + id_listing;
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.get("workerId").equals(null)) {
                                    txtNoHay.setVisibility(View.VISIBLE);
                                    card_worker.setVisibility(View.GONE);
                                } else {
                                    id_worker = response.getInt("workerId");
                                    getWorker();
                                    txtNoHay.setVisibility(View.GONE);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            txtNoHay.setVisibility(View.GONE);

                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);

        } catch (Exception e) {
            Log.e("TAG", "getMyListing: ", e);
        }
    }



    private void getWorker() {
        try {
            String url = Uris.API_ENDPOINT + "/worker/" + id_worker;
            final JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("Data worker:"+response.toString());
                            try{
                                txt_name.setText(response.getString("fullName"));
                                JSONArray jobs = response.getJSONArray("jobs");
                                txt_job.setText(jobs.getJSONObject(0).getString("name"));
                               Picasso.get().load(Uris.IMAGES_ENDPOINT+response.getString("image")).into(img_profile);
                               card_worker.setVisibility(View.VISIBLE);

                            }catch (Exception e){
                                Log.e("Error", "onResponse: ",e );
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            txtNoHay.setVisibility(View.GONE);
                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);

        } catch (Exception e) {
            Log.e("TAG", "getMyListing: ", e);
        }
    }
}