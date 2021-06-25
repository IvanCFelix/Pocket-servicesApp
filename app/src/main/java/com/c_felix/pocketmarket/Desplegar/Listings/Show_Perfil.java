package com.c_felix.pocketmarket.Desplegar.Listings;

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
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_Perfil extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter_listing_worker adapter;
    TextView txtNoHay, txtName , txtEmail;
    CircleImageView profileImage;
    private RequestQueue queue;
    public Show_Perfil() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_show_perfil ,container, false);

        txtNoHay = view.findViewById(R.id.txtNoHay);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        queue = Volley.newRequestQueue(getContext());
        txtName = view.findViewById(R.id.txt_name);
        txtEmail = view.findViewById(R.id.navheader_correo);
        profileImage = view.findViewById(R.id.navheader_civ);



        getActivity().setTitle(" Mi perfil");
        try{
            JSONObject user = new JSONObject(SQLITE.obtenerUsuarioActivo(getContext()).get(0).getUser());
            txtName.setText(user.getString("fullName"));
            txtEmail.setText(user.getString("email"));
            Picasso.get().load(Uris.IMAGES_ENDPOINT+user.getString("image")).into(profileImage);
        }catch (Exception e){
            Log.e("Error seteando", "onCreateView: ",e );
        }
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

            }
        });
    }

    private void getProfile(){
        try{
            JSONObject user = new JSONObject(SQLITE.obtenerUsuarioActivo(getContext()).get(0).getUser());
            String url = Uris.API_ENDPOINT+"/user/"+user.get("id");
            JsonArrayRequest request = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);

        }catch (Exception e){
            Log.e("TAG", "getProfile: ",e );

        }

    }
}
