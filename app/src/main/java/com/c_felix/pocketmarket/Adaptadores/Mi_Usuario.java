package com.c_felix.pocketmarket.Adaptadores;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.SQLITE;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mi_Usuario extends Fragment {
    CircleImageView civPerfil;

    SwipeRefreshLayout swipeRefreshLayout;

    String Usuario;
    ArrayList<UsuarioActivo> usuarioActivo = new ArrayList<>();
    TextView txtNombre, txtCorreo;

    public Mi_Usuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_mi__usuario, container, false);
        civPerfil = view.findViewById(R.id.civ_perfil);
        txtCorreo = view.findViewById(R.id.txt_perfil_correo);
        txtNombre = view.findViewById(R.id.txt_perfil_nombre);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen);


        getActivity().setTitle(" " + getString(R.string.mi_usuario));


        txtNombre.setText("Ejemplo");
        txtCorreo.setText("Data");
        civPerfil.setImageBitmap(null);

        return  view;
    }

}
