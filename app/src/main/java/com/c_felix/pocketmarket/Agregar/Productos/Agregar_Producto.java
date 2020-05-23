package com.c_felix.pocketmarket.Agregar.Productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.c_felix.pocketmarket.Adaptadores.SlideViewPagerImagenes;
import com.c_felix.pocketmarket.Clases.Multimedia;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.io.IOException;
import java.util.ArrayList;

public class Agregar_Producto extends AppCompatActivity {
    EditText titulo,descripcion,precio,cantidad_disp;
    Spinner tipoUnidad, categoria;
    TextView txtNoHay;
    Button agregar;
    ViewPager viewPager;
    TabLayout tabLayout;
    SlideViewPagerImagenes adapter;
    ArrayList<UsuarioActivo> usuarioActivos = new ArrayList<>();
    ArrayList<Multimedia> multimedia = new ArrayList<>();
    ImageView ivImagenes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__producto);
        setTitle("Agregar un producto");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cerrar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.vpMedia);
        tabLayout = findViewById(R.id.tabDots);
        txtNoHay = findViewById(R.id.txtNoHay);
        viewPager = findViewById(R.id.vpMedia);
        tabLayout = findViewById(R.id.tabDots);
        txtNoHay = findViewById(R.id.txtNoHay);
        agregar = findViewById(R.id.btn_agg_producto);
        ivImagenes = findViewById(R.id.iv_RegistrarFoto);
        tipoUnidad = findViewById(R.id.spn_tipoUnidad);
        categoria = findViewById(R.id.spn_categoria_producto);
        titulo = findViewById(R.id.txt_titulo_producto);
        descripcion = findViewById(R.id.txt_descripcion_producto);
        precio = findViewById(R.id.txt_precioProducto);
        cantidad_disp = findViewById(R.id.txt_stockProducto);

        ArrayList<String> tipoUnidades = new ArrayList<>();

        tipoUnidades.add("Tipo de medicion..");
        tipoUnidades.add("Unidad");
        tipoUnidades.add("Kilogramo");
        tipoUnidades.add("Litros");

        tipoUnidad.setAdapter(new ArrayAdapter<>(Agregar_Producto.this,R.layout.spinner_item,tipoUnidades));

        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Seleccionar categoria..");
        categorias.add("Alimentos");
        categorias.add("Bebidas");
        categorias.add("Ropa");
        categorias.add("Otro");

        categoria.setAdapter(new ArrayAdapter<>(Agregar_Producto.this,R.layout.spinner_item,categorias));

        ivImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multimedia.size() < 5) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Agregar_Producto.this);
                    dialogo1.setTitle(getString(R.string.imagenes));
                    dialogo1.setMessage(getString(R.string.donde_desea_obtener_imagen));
                    dialogo1.setPositiveButton(getString(R.string.galeria), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Metodos_Estaticos.abrirGaleria(Agregar_Producto.this, Metodos_Estaticos.CODIGO_GALERIA_FOTO);
                        }
                    });
                    dialogo1.setNegativeButton(getString(R.string.camara), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Metodos_Estaticos.tomarImagen(Agregar_Producto.this, Agregar_Producto.this, Metodos_Estaticos.CODIGO_CAMARA_FOTO);
                        }
                    });
                    dialogo1.show();
                } else {

                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Metodos_Estaticos.CODIGO_CAMARA_FOTO) {
                obtenerImagenCamara();
            } else if (requestCode == Metodos_Estaticos.CODIGO_GALERIA_FOTO) {
                obtenerImagenGaleria(data);
            }
        }
    }
    void obtenerImagenGaleria(Intent data) {
        Uri selectedImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            bitmap = Metodos_Estaticos.comprimirImagen(bitmap, 480);
            multimedia.add(new Multimedia(obtenerID_Imagen(), usuarioActivos.get(0).getID(), bitmap));
            adapter.notifyDataSetChanged();
            validarlista();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void obtenerImagenCamara() {
        Bitmap foto = BitmapFactory.decodeFile(Metodos_Estaticos.direccion_Imagen, new BitmapFactory.Options());
        foto = Metodos_Estaticos.corregirRotacion(Metodos_Estaticos.direccion_Imagen, foto);
        if (foto != null) {
            Metodos_Estaticos.eliminarFoto(Metodos_Estaticos.direccion_Imagen);
            multimedia.add(new Multimedia(obtenerID_Imagen(), usuarioActivos.get(0).getID(),foto));
            adapter.notifyDataSetChanged();
            validarlista();
        }
    }


    void validarlista() {
        if (multimedia.isEmpty()) {
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            txtNoHay.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            txtNoHay.setVisibility(View.GONE);
        }
    }
    int obtenerID_Imagen() {
        int id = SQLITE.obtenerValorMaximo(Agregar_Producto.this, SQLITE.tablaMultimedia, "ID") + 1;
        return id;
    }

}
