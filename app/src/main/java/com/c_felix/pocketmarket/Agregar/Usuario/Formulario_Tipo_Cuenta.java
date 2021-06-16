package com.c_felix.pocketmarket.Agregar.Usuario;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.c_felix.pocketmarket.R;

import java.util.ArrayList;


public class Formulario_Tipo_Cuenta extends Fragment {
    Spinner spn_tipoCuenta;
    ArrayList<String> listaRoles = new ArrayList<>();
    LinearLayout lyDatos;
    TextInputLayout empresa;
    EditText txt_empresa, txt_calle,txt_colonia,txt_numExt;

    public Formulario_Tipo_Cuenta() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_formulario__tipo__cuenta, container, false);

        spn_tipoCuenta = view.findViewById(R.id.spn_tipoUsuario);
        lyDatos = view.findViewById(R.id.ly_datos);
        empresa = view.findViewById(R.id.ty_NombreEmpresa);
        txt_empresa = view.findViewById(R.id.txt_nomEmpresa);
        
        listaRoles.add("Tipo de cuenta..");
        listaRoles.add("Vendedor");
        listaRoles.add("Comprador");
        spn_tipoCuenta.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, listaRoles));

        spn_tipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spn_tipoCuenta.getSelectedItemPosition()==1){
                    lyDatos.setVisibility(View.VISIBLE);
                    empresa.setVisibility(View.VISIBLE);

                }else if(spn_tipoCuenta.getSelectedItemPosition()==2) {
                    lyDatos.setVisibility(View.VISIBLE);
                    empresa.setVisibility(View.GONE);
                }else{
                    lyDatos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return  view;
    }
}
