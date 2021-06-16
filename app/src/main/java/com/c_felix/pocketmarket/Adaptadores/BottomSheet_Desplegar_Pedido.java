package com.c_felix.pocketmarket.Adaptadores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c_felix.pocketmarket.R;


import org.json.JSONArray;

import java.util.ArrayList;


public class BottomSheet_Desplegar_Pedido extends BottomSheetDialogFragment {

    TextView txtNombreTarea, txt_ingeniero_nombre, txt_sublote_nombre, txt_fecha_limite,txt_fecha_inicio;
    TextView txt_descripcion;
    Button btn_cerrar,btn_crearReporte;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet__desplegar__tarea, container, false);
        txtNombreTarea = view.findViewById(R.id.txt_nombre_tarea);
        txt_sublote_nombre = view.findViewById(R.id.txt_sublote_nombre);
        btn_cerrar = view.findViewById(R.id.btn_cerrar);
        txt_fecha_limite = view.findViewById(R.id.txt_fecha_limite);
        btn_crearReporte = view.findViewById(R.id.btn_reporte);
        final double distancia = 30.0;
        btn_crearReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (distancia <= 10.0){
                    }else{
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Aun no has llegado a tu destino");
                        builder.setPositiveButton(getString(R.string.enterado), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        });
                        builder.show();
                    }
            }
        });
        return view;
    }
}
