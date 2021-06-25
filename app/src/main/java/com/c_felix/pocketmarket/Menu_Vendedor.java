package com.c_felix.pocketmarket;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.c_felix.pocketmarket.Adaptadores.Mi_Usuario;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Desplegar.Listings.Lista_MyListings;
import com.c_felix.pocketmarket.Desplegar.Listings.Show_Perfil;
import com.c_felix.pocketmarket.Listas.Listings.Job_Listing;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu_Vendedor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Mi_Usuario mi_usuario = new Mi_Usuario();
    Show_Perfil showPerfil = new Show_Perfil();
    Job_Listing lisints = new Job_Listing();
    Lista_MyListings lista_myListings = new Lista_MyListings();
    public static CircleImageView imagenPerfil;
    public static TextView txtNombre, txtCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.getMenu().getItem(0).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        Drawable icono = getResources().getDrawable(R.drawable.ic_casa);
        icono.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setIcon(icono);


        imagenPerfil = headerView.findViewById(R.id.navheader_civ);
        txtNombre = headerView.findViewById(R.id.navheader_Nombre);
        txtCorreo = headerView.findViewById(R.id.navheader_Correo);

        ArrayList<UsuarioActivo> usuario = SQLITE.obtenerUsuarioActivo(Menu_Vendedor.this);
        try{
            JSONObject user = new JSONObject(usuario.get(0).getUser());
            System.out.println(user.toString());
            Picasso.get().load(Uris.IMAGES_ENDPOINT+user.getString("image")).into(imagenPerfil);
            txtNombre.setText(user.getString("fullName"));
            txtCorreo.setText(user.getString("email"));
        }catch (JSONException e){
            System.out.println(e.toString());
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.area_ventana, lisints);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch (id) {
            case R.id.nav_Intro:
                try {
                    fragmentTransaction.replace(R.id.area_ventana,lisints);
                    drawer.closeDrawer(GravityCompat.START);
                    Drawable icono = getResources().getDrawable(R.drawable.ic_casa);
                    icono.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setIcon(icono);
                } catch (Exception e) {
                }

                break;
            case R.id.nav_pedidos:
                try {
                    fragmentTransaction.replace(R.id.area_ventana, lista_myListings);
                    drawer.closeDrawer(GravityCompat.START);
                    Drawable icono = getResources().getDrawable(R.drawable.ic_tareas);
                    icono.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setIcon(icono);
                } catch (Exception e) {
                }
                break;
                case R.id.nav_Mi_Usuario:
                try {
                  fragmentTransaction.replace(R.id.area_ventana, showPerfil);
                    drawer.closeDrawer(GravityCompat.START);
                    Drawable icono = getResources().getDrawable(R.drawable.ic_persona);
                     getSupportActionBar().setIcon(icono);
                } catch (Exception e) {
                }
                break;

            case R.id.nav_salir:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Menu_Vendedor.this);
                dialogo1.setTitle(getString(R.string.desea_cerrar_sesion));
                dialogo1.setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        SQLITE.limpiarTabla(Menu_Vendedor.this,SQLITE.tablaUsuarioActivo);
                        startActivity(new Intent(Menu_Vendedor.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();
                break;
        }
        fragmentTransaction.commit();

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void cambiarFragmento(Fragment fragmento) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        fragmentTransaction.replace(R.id.area_ventana, fragmento);
        drawer.closeDrawer(GravityCompat.START);
        fragmentTransaction.commit();
    }

}
