package com.c_felix.pocketmarket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Agregar.Usuario.Activity_Registrar_Usuario;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button registrarse, login;
    EditText username, password;
    public static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
    public static final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        registrarse = findViewById(R.id.btnRegistrarse);
        login = findViewById(R.id.btnIniciarSesion);
        username = findViewById(R.id.txt_Login_Usuario);
        password = findViewById(R.id.txt_Login_Contraseña);
        queue = Volley.newRequestQueue(this);
        PedirPermisos();
        try {
            if (SQLITE.obtenerUsuarioActivo(Login.this).size() != 0) {


                JSONObject usuario = new JSONObject(SQLITE.obtenerUsuarioActivo(Login.this).get(0).getUser());
                switch (usuario.getInt("role")) {
                    case 2:
                        startActivity(new Intent(Login.this, Menu_Vendedor.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(Login.this, Menu_Usuario.class));
                        finish();
                        break;
                    default:
                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject credentials = new JSONObject();
                try {
                    credentials.put("email", username.getText().toString());
                    credentials.put("password", password.getText().toString());
                    System.out.println("jsonString: " + credentials.toString());
                    String url = Uris.API_ENDPOINT + "/login";
                    JsonObjectRequest request = new JsonObjectRequest
                            (Request.Method.POST, url, credentials, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println(response.toString());

                                    try {
                                        if (response.getJSONObject("response") != null) {
                                            UsuarioActivo user = new UsuarioActivo(response.getJSONObject("response").toString(), response.getString("token"));
                                            SQLITE.agregarUsuarioActivo(Login.this, user);
                                            if (response.getJSONObject("response").getInt("role") == 2) {
                                                startActivity(new Intent(Login.this, Menu_Vendedor.class));
                                                finish();
                                            }else{
                                                startActivity(new Intent(Login.this, Menu_Usuario.class));
                                                finish();
                                            }

                                        }
                                    } catch (Exception error) {
                                        System.out.println(error.toString());
                                    }
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

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Activity_Registrar_Usuario.class));
            }
        });
    }


    public void PedirPermisos() {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            //Si alguno de los permisos no esta concedido lo solicita
            ActivityCompat.requestPermissions(this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        }
    }
}
