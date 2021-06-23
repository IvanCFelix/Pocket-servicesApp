package com.c_felix.pocketmarket.Agregar.Producto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Login;
import com.c_felix.pocketmarket.Menu_Vendedor;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Formulario_Jobs extends AppCompatActivity {
    EditText name, description;
    Spinner categoria;
    TextView txtNoHay;
    Button agregar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Bitmap bmpImagen;
    ImageView ivImagen, registrarImagen;
    ArrayList<UsuarioActivo> usuarioActivos = new ArrayList<>();
    private RequestQueue queue ;
    ArrayList<String> jobList = new ArrayList<>();
    ArrayList<JSONObject> jobsObjects = new ArrayList<>();

    int jobId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario__producto);

        setTitle("Crear trabajo");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cerrar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.vpMedia);
        tabLayout = findViewById(R.id.tabDots);
        txtNoHay = findViewById(R.id.txtNoHay);
        agregar = findViewById(R.id.btn_agg_listing);
        ivImagen = findViewById(R.id.iv_imagen);
        registrarImagen = findViewById(R.id.iv_RegistrarFoto);
        queue = Volley.newRequestQueue(Formulario_Jobs.this);
        categoria = findViewById(R.id.spn_job_category);
        name = findViewById(R.id.txt_name);
        description = findViewById(R.id.txt_description);
        usuarioActivos = SQLITE.obtenerUsuarioActivo(Formulario_Jobs.this);
        getJobs();



        categoria.setAdapter(new ArrayAdapter<>(Formulario_Jobs.this, R.layout.support_simple_spinner_dropdown_item, jobList));

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        registrarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Formulario_Jobs.this);
                dialogo1.setTitle(getString(R.string.imagenes));
                dialogo1.setMessage(getString(R.string.donde_desea_obtener_imagen));
                dialogo1.setPositiveButton(getString(R.string.galeria), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.abrirGaleria(Formulario_Jobs.this, Metodos_Estaticos.CODIGO_GALERIA_FOTO);
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.camara), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.tomarImagen(Formulario_Jobs.this, Formulario_Jobs.this, Metodos_Estaticos.CODIGO_CAMARA_FOTO);
                    }
                });
                dialogo1.show();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText() == null || description.getText() == null || bmpImagen == null) {
                    Toast.makeText(Formulario_Jobs.this, "Complete los datos correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        JSONObject listing = new JSONObject();
                       ArrayList<UsuarioActivo> usuario = SQLITE.obtenerUsuarioActivo(Formulario_Jobs.this);
                       JSONObject user = new JSONObject(usuario.get(0).getUser());
                        listing.put("name",name.getText().toString());
                        listing.put("description",description.getText().toString());
                        listing.put("jobId",1);
                        listing.put("userId",user.get("id"));
                        listing.put("image",Metodos_Estaticos.convertToBase64(bmpImagen));
                        registerListing(listing);
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }

                }
            }
        });
    }

    void obtenerImagenCamara() {
        Bitmap foto = BitmapFactory.decodeFile(Metodos_Estaticos.direccion_Imagen, new BitmapFactory.Options());
        foto = Metodos_Estaticos.corregirRotacion(Metodos_Estaticos.direccion_Imagen, foto);
        if (foto != null) {
            foto = Metodos_Estaticos.comprimirImagen(foto, 480);
            bmpImagen = foto;
            ivImagen.setVisibility(View.VISIBLE);
            ivImagen.setImageBitmap(foto);
            Metodos_Estaticos.eliminarFoto(Metodos_Estaticos.direccion_Imagen);
        }
    }

    public void registerListing(JSONObject listing) {
        try {

            String url = Uris.API_ENDPOINT + "/listing";
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.POST, url, listing, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Formulario_Jobs.this);
                            dialogo1.setCancelable(false);
                            dialogo1.setMessage("Trabajo registrado exitosamente");
                            dialogo1.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    finish();
                                }
                            });
                            dialogo1.show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);
        } catch (Exception error) {
        }
    }


    public void getJobs() {
        try {
            String url = Uris.API_ENDPOINT + "/job";
            JsonArrayRequest request = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i=0; i < response.length(); i++) {
                              try{
                                  JSONObject job = response.getJSONObject(i);
                                  jobList.add(job.getString("name"));
                                  jobsObjects.add(job);
                              }catch (JSONException e){

                              }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            queue.add(request);
        } catch (Error error) {
        }
    }


    void obtenerImagenGaleria(Intent data) {
        Uri selectedImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            bitmap = Metodos_Estaticos.comprimirImagen(bitmap, 480);
            bmpImagen = bitmap;
            ivImagen.setVisibility(View.VISIBLE);
            registrarImagen.setVisibility(View.GONE);
            ivImagen.setImageBitmap(bmpImagen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Metodos_Estaticos.CODIGO_CAMARA_FOTO) {
                obtenerImagenCamara();
            } else if (requestCode == Metodos_Estaticos.CODIGO_GALERIA_FOTO) {
                obtenerImagenGaleria(data);
            }
        }
    }
}
