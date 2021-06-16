package com.c_felix.pocketmarket.Desplegar.Pedidos;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.c_felix.pocketmarket.Adaptadores.BottomSheet_Desplegar_Pedido;
import com.c_felix.pocketmarket.Clases.Carrito;
import com.c_felix.pocketmarket.Clases.Usuarios;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;
import com.c_felix.pocketmarket.Utilidades.SQLITE;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;

public class Mapa_Ubicacion_Pedido extends AppCompatActivity implements OnMapReadyCallback {
    LatLng coordenadas;
    private GoogleMap mMap;
    BottomSheet_Desplegar_Pedido bottomSheet_desplegar_pedido = new BottomSheet_Desplegar_Pedido();
    private FusedLocationProviderClient fusedLocationClient;
    private LinearLayout informacion;
    private MapView mapView;
    ArrayList<Carrito> carritos = new ArrayList<>();
    Usuarios usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa__ubicacion__pedido);
        informacion = findViewById(R.id.mostrar_btmsheet);
        mapView = findViewById(R.id.mapview);
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        mapView.onResume();
        carritos = SQLITE.obtenerCarrito(Mapa_Ubicacion_Pedido.this);

        usuario= SQLITE.obtenerUsuario(Mapa_Ubicacion_Pedido.this,carritos.get(0).getID_Producto());
        informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int marcador = -1;
                Bundle bundle = new Bundle();
                bundle.putInt("marcador", marcador);
                bottomSheet_desplegar_pedido.setArguments(bundle);
                bottomSheet_desplegar_pedido.show(getSupportFragmentManager(), "Bottom sheet pedido");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Mapa_Ubicacion_Pedido.this);
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(Mapa_Ubicacion_Pedido.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Mapa_Ubicacion_Pedido.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        try{
            JSONArray JSONcoordenadas_tarea  = new JSONArray("[" + usuario.getUbicacion() + "]");
            for (int i = 0; i < JSONcoordenadas_tarea.length(); i++) {
                coordenadas = new LatLng(Double.parseDouble(JSONcoordenadas_tarea.getJSONObject(i).getString("Lat")), Double.parseDouble(JSONcoordenadas_tarea.getJSONObject(i).getString("Lng")));
            }
        }catch (Exception e){}
        LatLngBounds latLngBounds = Metodos_Estaticos.obtenerCentroMapa(coordenadas);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100));
        dibujarCampo();
    }


    public void dibujarCampo() {
        Marker marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .draggable(false)
                .title("Ubicacion"));
        marcador.setTag(String.valueOf(1));
    }
}
