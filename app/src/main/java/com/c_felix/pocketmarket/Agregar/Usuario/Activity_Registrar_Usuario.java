package com.c_felix.pocketmarket.Agregar.Usuario;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Adaptadores.SlideViewPager;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Registrar_Usuario extends AppCompatActivity {
    ViewPager paginas;
    SlideViewPager adaptador;
    FloatingActionButton fabSiguiente, fabAtras;
    Formulario_Datos_Usuario formulario_datos_usuario = new Formulario_Datos_Usuario();

    List<Fragment> lista = new ArrayList<>();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar__usuario);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cerrar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Registrarse");
        fabSiguiente = findViewById(R.id.fab_siguiente);
        fabAtras = findViewById(R.id.fab_atras);
        queue = Volley.newRequestQueue(this);

        lista.add(formulario_datos_usuario);

        paginas = findViewById(R.id.vpIntro);
        adaptador = new SlideViewPager(getSupportFragmentManager(), lista);
        paginas.setAdapter(adaptador);

        fabAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paginas.getCurrentItem() == 1) {
                    paginas.setCurrentItem(0);


                } else if (paginas.getCurrentItem() == 2) {
                    paginas.setCurrentItem(1);

                }
            }
        });


        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (formulario_datos_usuario.txt_adress.getText() == null || formulario_datos_usuario.txtCorreo.getText() == null || formulario_datos_usuario.txtNombre.getText() == null
                        || formulario_datos_usuario.bmpImagen == null || formulario_datos_usuario.genero == 0) {
                    Toast.makeText(Activity_Registrar_Usuario.this, "Revise que todos los datos esten llenos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject user = new JSONObject();
                        String imagen = "data:image/jpeg;base64,"+ Metodos_Estaticos.convertToBase64(formulario_datos_usuario.bmpImagen);
                        user.put("fullName", formulario_datos_usuario.txtNombre.getText().toString());
                        user.put("email", formulario_datos_usuario.txtCorreo.getText().toString());
                        user.put("password", formulario_datos_usuario.txt_password.getText().toString());
                        user.put("address", formulario_datos_usuario.txt_adress.getText().toString());
                        user.put("age", Integer.parseInt(formulario_datos_usuario.txt_age.getText().toString()));
                        user.put("gender", formulario_datos_usuario.genero - 1);
                        user.put("image", Metodos_Estaticos.convertToBase64(formulario_datos_usuario.bmpImagen));
                        user.put("roleId",3);
                        System.out.println(user.get("image"));

                        addUser(user);
                    } catch (Exception e) {
                        Log.e("TAG", "onClick: ", e);
                    }


                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Metodos_Estaticos.CODIGO_CAMARA_FOTO) {
                formulario_datos_usuario.obtenerImagenCamara();
            } else if (requestCode == Metodos_Estaticos.CODIGO_GALERIA_FOTO) {
                formulario_datos_usuario.obtenerImagenGaleria(data);
            }
        }
    }

    private void addUser(JSONObject user) {
        String url = Uris.API_ENDPOINT+"/user";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, url, user, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Toast.makeText(Activity_Registrar_Usuario.this,
                                "Se registro correctamente", Toast.LENGTH_LONG).show();
                        finish();
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


