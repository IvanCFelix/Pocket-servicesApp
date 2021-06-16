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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.io.IOException;
import java.util.ArrayList;

public class Formulario_Producto extends AppCompatActivity {
    EditText titulo,descripcion,precio,cantidad_disp;
    Spinner tipoUnidad, categoria;
    TextView txtNoHay;
    Button agregar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Bitmap bmpImagen;
    ImageView ivImagen, registrarImagen;
    ArrayList<UsuarioActivo> usuarioActivos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario__producto);

        setTitle("Agregar un producto");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cerrar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.vpMedia);
        tabLayout = findViewById(R.id.tabDots);
        txtNoHay = findViewById(R.id.txtNoHay);
        agregar = findViewById(R.id.btn_agg_producto);
        ivImagen = findViewById(R.id.iv_imagen);
        registrarImagen = findViewById(R.id.iv_RegistrarFoto);
        tipoUnidad = findViewById(R.id.spn_tipoUnidad);
        categoria = findViewById(R.id.spn_categoria_producto);
        titulo = findViewById(R.id.txt_titulo_producto);
        descripcion = findViewById(R.id.txt_descripcion_producto);
        precio = findViewById(R.id.txt_precioProducto);
        cantidad_disp = findViewById(R.id.txt_stockProducto);
        usuarioActivos = SQLITE.obtenerUsuarioActivo(Formulario_Producto.this);
        ArrayList<String> tipoUnidades = new ArrayList<>();

        tipoUnidades.add("Tipo de medicion..");
        tipoUnidades.add("Unidad");
        tipoUnidades.add("Kilogramo");
        tipoUnidades.add("Litros");

        tipoUnidad.setAdapter(new ArrayAdapter<>(Formulario_Producto.this,R.layout.spinner_item,tipoUnidades));

        final ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Seleccionar categoria..");
        categorias.add("Alimentos");
        categorias.add("Bebidas");
        categorias.add("Ropa");
        categorias.add("Otro");

        categoria.setAdapter(new ArrayAdapter<>(Formulario_Producto.this,R.layout.spinner_item,categorias));


        registrarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Formulario_Producto.this);
                dialogo1.setTitle(getString(R.string.imagenes));
                dialogo1.setMessage(getString(R.string.donde_desea_obtener_imagen));
                dialogo1.setPositiveButton(getString(R.string.galeria), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.abrirGaleria(Formulario_Producto.this, Metodos_Estaticos.CODIGO_GALERIA_FOTO);
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.camara), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.tomarImagen(Formulario_Producto.this, Formulario_Producto.this, Metodos_Estaticos.CODIGO_CAMARA_FOTO);
                    }
                });
                dialogo1.show();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titulo.getText()==null||descripcion.getText()==null||tipoUnidad.getSelectedItemPosition()==0||cantidad_disp.getText()==null||precio.getText()==null||categoria.getSelectedItemPosition()==0 || bmpImagen==null){
                    Toast.makeText(Formulario_Producto.this, "Complete los datos correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Productos producto = new Productos();
                    producto.setID(Metodos_Estaticos.obtenerValorMaximo(Formulario_Producto.this, SQLITE.tablaProductos,"ID")+1);
                    producto.setID_usuario(usuarioActivos.get(0).getID());
                    producto.setTitulo(titulo.getText().toString().trim());
                    producto.setDescripcion(descripcion.getText().toString().trim());
                    producto.setUnidadMedida(tipoUnidad.getSelectedItem().toString());
                    producto.setCategoria(categoria.getSelectedItem().toString());
                    producto.setInventario(Integer.parseInt(cantidad_disp.getText().toString()));
                    producto.setPrecioUnidad(Double.parseDouble(precio.getText().toString()));
                    producto.setImagen(bmpImagen);
                    SQLITE.agregarProducto(Formulario_Producto.this,producto,"jpg");
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Formulario_Producto.this);
                    dialogo1.setCancelable(false);
                    dialogo1.setMessage("Producto registrado exitosamente");
                    dialogo1.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            finish();
                        }
                    });
                    dialogo1.show();
                }
            }
        });
    }

    void obtenerImagenCamara() {
        Bitmap foto = BitmapFactory.decodeFile(Metodos_Estaticos.direccion_Imagen, new BitmapFactory.Options());
        foto= Metodos_Estaticos.corregirRotacion(Metodos_Estaticos.direccion_Imagen, foto);
        if(foto!=null) {
            foto= Metodos_Estaticos.comprimirImagen(foto, 480);
            bmpImagen = foto;
            ivImagen.setVisibility(View.VISIBLE);
            ivImagen.setImageBitmap(foto);
            Metodos_Estaticos.eliminarFoto(Metodos_Estaticos.direccion_Imagen);
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
