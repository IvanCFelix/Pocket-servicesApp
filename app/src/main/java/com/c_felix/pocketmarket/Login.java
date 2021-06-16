package com.c_felix.pocketmarket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c_felix.pocketmarket.Agregar.Usuario.Activity_Registrar_Usuario;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button registrarse,login;
    EditText username, password;
    public static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
    public static final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    ArrayList<UsuarioActivo> activo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        registrarse = findViewById(R.id.btnRegistrarse);
        login = findViewById(R.id.btnIniciarSesion);
        username =findViewById(R.id.txt_Login_Usuario);
        password=findViewById(R.id.txt_Login_Contraseña);

        PedirPermisos();
        activo = SQLITE.obtenerUsuarioActivo(Login.this);
        if(activo.size()!=0){
            startActivity(new Intent(Login.this, Menu_Vendedor.class).putExtra("Usuario",activo.get(0).getID()));
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuarios usuario = SQLITE.obtenerUsuarioUsername(Login.this,username.getText().toString());
                if(usuario==null){
                    Toast.makeText(Login.this, "Nombre de usuario incorrecto", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.getText().toString().equals(usuario.getContraseña())){
                        startActivity(new Intent(Login.this, Menu_Vendedor.class).putExtra("Usuario",usuario.getID()));
                        UsuarioActivo usuarioActivo = new UsuarioActivo();
                        usuarioActivo.setID(usuario.getID());
                        usuarioActivo.setUsername(usuario.getUsername());
                        SQLITE.usuarioActivo(Login.this,usuarioActivo);
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Contraseña incorrecta   ", Toast.LENGTH_SHORT).show();

                    }
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
