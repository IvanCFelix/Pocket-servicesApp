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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.c_felix.pocketmarket.Adaptadores.SlideViewPager;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Registrar_Usuario extends AppCompatActivity {
    ViewPager paginas;
    SlideViewPager adaptador;
    FloatingActionButton fabSiguiente, fabAtras;
    Formulario_Datos_Usuario formulario_datos_usuario = new Formulario_Datos_Usuario();
    Formulario_Tipo_Cuenta formulario_tipo_cuenta = new Formulario_Tipo_Cuenta();
    Mapa_Ubicacion_Usuario mapa_ubicacion_usuario = new Mapa_Ubicacion_Usuario();
    List<Fragment> lista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar__usuario);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cerrar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Agregar Usuario");
        fabSiguiente = findViewById(R.id.fab_siguiente);
        fabAtras = findViewById(R.id.fab_atras);

        lista.add(formulario_tipo_cuenta);
        lista.add(mapa_ubicacion_usuario);
        lista.add(formulario_datos_usuario);

        paginas = findViewById(R.id.vpIntro);
        adaptador = new SlideViewPager(getSupportFragmentManager(), lista);
        paginas.setAdapter(adaptador);

        fabAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paginas.getCurrentItem() == 1) {
                    paginas.setCurrentItem(0);


                }else if(paginas.getCurrentItem()==2){
                    paginas.setCurrentItem(1);

                }
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paginas.getCurrentItem() == 0 ) {
                    if(formulario_tipo_cuenta.spn_tipoCuenta.getSelectedItemPosition()==0 || (formulario_tipo_cuenta.spn_tipoCuenta.getSelectedItemPosition()==1 && formulario_tipo_cuenta.txt_empresa.getText().equals(""))){
                        Toast.makeText(Activity_Registrar_Usuario.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        paginas.setCurrentItem(1);
                        Toast.makeText(Activity_Registrar_Usuario.this, "Seleccione su ubicacion", Toast.LENGTH_SHORT).show();


                    }
                }else if(paginas.getCurrentItem()==1){
                    if(mapa_ubicacion_usuario.coordenadas==null){
                        Toast.makeText(Activity_Registrar_Usuario.this, "Seleccione su ubicacion", Toast.LENGTH_SHORT).show();

                    }else{
                        paginas.setCurrentItem(2);


                    }
                }
                else{
                    if(formulario_datos_usuario.txt_Username.getText() == null|| formulario_datos_usuario.txtCorreo.getText() == null || formulario_datos_usuario.txtNombre.getText() == null
                   ||formulario_datos_usuario.txtTelefono.getText()==null||formulario_datos_usuario.bmpImagen==null ){
                        Toast.makeText(Activity_Registrar_Usuario.this, "Revise que todos los datos esten llenos", Toast.LENGTH_SHORT).show();
                    }else{
                        Usuarios usuario = new Usuarios();
                        usuario.setID(SQLITE.obtenerValorMaximo(Activity_Registrar_Usuario.this,SQLITE.tablaUsuarios,"ID")+1);
                        usuario.setNombre(formulario_datos_usuario.txtNombre.getText().toString());
                        usuario.setNombreEmpresa(formulario_tipo_cuenta.txt_empresa.getText().toString());
                        usuario.setUsername(formulario_datos_usuario.txt_Username.getText().toString());
                        usuario.setCorreo(formulario_datos_usuario.txtCorreo.getText().toString());
                        usuario.setContraseña(formulario_datos_usuario.txt_password.getText().toString());
                        usuario.setTipoUsuario(formulario_tipo_cuenta.spn_tipoCuenta.getSelectedItem().toString());
                        usuario.setNumeroTel(formulario_datos_usuario.txtTelefono.getText().toString());
                        JSONObject ubicacion = new JSONObject();
                        try {
                            ubicacion.put("Lat",mapa_ubicacion_usuario.coordenadas.latitude);
                            ubicacion.put("Lng", mapa_ubicacion_usuario.coordenadas.longitude);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        usuario.setUbicacion(ubicacion.toString());
                        usuario.setImagen(formulario_datos_usuario.bmpImagen);

                        try {
                            SQLITE.agregarUsuario(Activity_Registrar_Usuario.this, usuario, "jpg");
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Activity_Registrar_Usuario.this);
                            dialogo1.setCancelable(false);
                            dialogo1.setMessage("Usuario registrado exitosamente");
                            dialogo1.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    finish();
                                }
                            });
                            dialogo1.show();
                        }catch ( Exception e){
                            Toast.makeText(Activity_Registrar_Usuario.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        paginas.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    fabSiguiente.setImageResource((R.drawable.ic_siguiente));
                    fabAtras.setVisibility(View.GONE);
                } else if( i ==1){
                    fabSiguiente.setImageResource((R.drawable.ic_siguiente));
                    fabAtras.setVisibility(View.VISIBLE);
                }
                else {
                    fabSiguiente.setImageResource((R.drawable.ic_finalizar));
                    fabAtras.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

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
    }


