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

import com.c_felix.pocketmarket.Agregar.Producto.Formulario_Jobs;
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
        titulo = findViewById(R.id.txt_titulo_producto);
        descripcion = findViewById(R.id.txt_descripcion_producto);
        empresa = findViewById(R.id.txt_empresa);
        disponible = findViewById(R.id.txt_stockProducto);
        precio = findViewById(R.id.txt_precioProducto);
        agregar_carrito= findViewById(R.id.btn_agg_producto);
        usuarioActivos = SQLITE.obtenerUsuarioActivo(Activity_Desplegar_Producto.this);
        String empresaText= "De: "+usuario.getNombreEmpresa();
        empresa.setText(empresaText);

        ArrayList<String> cantidades = new ArrayList<>();
        cantidades.add("Cantidad:");

        cantidad.setAdapter(new ArrayAdapter<>(Activity_Desplegar_Producto.this,R.layout.spinner_item,cantidades));

        agregar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });


    }
}
