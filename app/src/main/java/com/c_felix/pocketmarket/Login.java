package com.c_felix.pocketmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.c_felix.pocketmarket.Añadir.Usuario.Activity_Registrar_Usuario;

public class Login extends AppCompatActivity {
        Button registrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        registrarse = findViewById(R.id.btnRegistrarse);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Activity_Registrar_Usuario.class));
            }
        });
    }
}
