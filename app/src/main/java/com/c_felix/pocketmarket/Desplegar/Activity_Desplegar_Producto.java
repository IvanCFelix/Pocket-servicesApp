package com.c_felix.pocketmarket.Desplegar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.c_felix.pocketmarket.Agregar.Producto.Formulario_Producto;
import com.c_felix.pocketmarket.Clases.Carrito;
import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.Listas.Carrito.Lista_Carrito;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

public class Activity_Desplegar_Producto extends AppCompatActivity {
ImageView ivImagen;
TextView titulo,disponible,precio, empresa;
EditText descripcion;
int ID_Producto;
Button agregar_carrito;
Spinner cantidad;
ArrayList<UsuarioActivo>usuarioActivos = new ArrayList<>();
Usuarios usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplegar_producto);
        ivImagen = findViewById(R.id.iv_RegistrarFoto);
       cantidad = findViewById(R.id.spn_cantidad);
        Intent intent = getIntent();
        ID_Producto =  intent.getIntExtra("Producto",0);
        final Productos producto= SQLITE.obtenerProducto(Activity_Desplegar_Producto.this,ID_Producto);
        setTitle(producto.getTitulo());
        titulo = findViewById(R.id.txt_titulo_producto);
        descripcion = findViewById(R.id.txt_descripcion_producto);
        empresa = findViewById(R.id.txt_empresa);
        disponible = findViewById(R.id.txt_stockProducto);
        precio = findViewById(R.id.txt_precioProducto);
        agregar_carrito= findViewById(R.id.btn_agg_producto);
        usuarioActivos = SQLITE.obtenerUsuarioActivo(Activity_Desplegar_Producto.this);
        usuario = SQLITE.obtenerUsuario(Activity_Desplegar_Producto.this,usuarioActivos.get(0).getID());
        ivImagen.setImageBitmap(producto.getImagen());
        titulo.setText(producto.getTitulo());
        String empresaText= "De: "+usuario.getNombreEmpresa();
        empresa.setText(empresaText);
        descripcion.setText(producto.getDescripcion());
        String stock = producto.getInventario()+" "+producto.getUnidadMedida();
        disponible.setText(stock);
        precio.setText(String.valueOf(producto.getPrecioUnidad()));
        ArrayList<String> cantidades = new ArrayList<>();
        cantidades.add("Cantidad:");
        for (int i=1; i<producto.getInventario();i++){
            cantidades.add(String.valueOf(i));
        }
        cantidad.setAdapter(new ArrayAdapter<>(Activity_Desplegar_Producto.this,R.layout.spinner_item,cantidades));

        agregar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Carrito> carritos =SQLITE.obtenerCarrito(Activity_Desplegar_Producto.this);
                if(carritos.size()==0){
                    Carrito carrito= new Carrito();
                    carrito.setID(Metodos_Estaticos.obtenerValorMaximo(Activity_Desplegar_Producto.this,SQLITE.tablaCarrito,"ID")+1);
                    carrito.setID_Producto(producto.getID());
                    carrito.setID_Usuario(usuarioActivos.get(0).getID());
                    carrito.setCantidad(Integer.parseInt(cantidad.getSelectedItem().toString()));
                    try {
                        SQLITE.agregarCarrito(Activity_Desplegar_Producto.this,carrito);
                    }catch (Exception e){
                        Toast.makeText(Activity_Desplegar_Producto.this, e+"", Toast.LENGTH_SHORT).show();

                    }
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Activity_Desplegar_Producto.this);
                    dialogo1.setCancelable(false);
                    dialogo1.setMessage("Agregador al carrito");
                    dialogo1.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            finish();
                        }
                    });
                    dialogo1.show();
                }else{
                    Toast.makeText(Activity_Desplegar_Producto.this, "Ya tiene una compra pendiente", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
}
