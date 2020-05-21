package com.c_felix.pocketmarket.Añadir.Usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Metodos_Estaticos;

import java.io.File;
import java.io.IOException;

public class Formulario_Datos_Usuario extends Fragment {
    ImageView ivImagen;
    FloatingActionButton fabAgregarImagen;
    EditText txtNombre, txt_Username, txtCorreo, txtTelefono,txt_password;
    Bitmap bmpImagen;

    public Formulario_Datos_Usuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_formulario__datos__usuario, container, false);

        ivImagen = view.findViewById(R.id.iv_Imagen);
        fabAgregarImagen = view.findViewById(R.id.fab_agregar_imagen);
        txtNombre = view.findViewById(R.id.txt_Nombre);
        txtCorreo = view.findViewById(R.id.txt_Correo);
        txtTelefono = view.findViewById(R.id.txt_Telefono);
        txt_Username = view.findViewById(R.id.txt_Username);
        txt_password = view.findViewById(R.id.txt_password);

        fabAgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle(getString(R.string.imagenes));
                dialogo1.setMessage(getString(R.string.donde_desea_obtener_imagen));
                dialogo1.setPositiveButton(getString(R.string.galeria), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.abrirGaleria(getActivity(), Metodos_Estaticos.CODIGO_GALERIA_FOTO);
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.camara), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Metodos_Estaticos.tomarImagen(getActivity(), getContext(), Metodos_Estaticos.CODIGO_CAMARA_FOTO);
                    }
                });
                dialogo1.show();
            }
        });

         return  view;
    }

    void obtenerImagenCamara() {
        Bitmap foto = BitmapFactory.decodeFile(Metodos_Estaticos.direccion_Imagen, new BitmapFactory.Options());
        foto= Metodos_Estaticos.corregirRotacion(Metodos_Estaticos.direccion_Imagen, foto);
        if(foto!=null) {
            foto= Metodos_Estaticos.comprimirImagen(foto, 480);
            bmpImagen = foto;
            ivImagen.setImageBitmap(foto);
            Metodos_Estaticos.eliminarFoto(Metodos_Estaticos.direccion_Imagen);
        }
    }

    void obtenerImagenGaleria(Intent data) {
        Uri selectedImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            bitmap = Metodos_Estaticos.comprimirImagen(bitmap, 480);
            bmpImagen = bitmap;
            ivImagen.setImageBitmap(bmpImagen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
