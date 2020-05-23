package com.c_felix.pocketmarket.Agregar.Productos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.c_felix.pocketmarket.R;

public class Agregar_Producto extends AppCompatActivity {
    EditText titulo,descripcion,precio,cantidad_disp;
    Spinner tipoUnidad, categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__producto);
    }
}
