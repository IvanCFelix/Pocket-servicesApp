package com.c_felix.pocketmarket.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.c_felix.pocketmarket.Clases.UsuarioActivo;

import java.util.ArrayList;

public class SQLITE {
    public static final String tablaUsuarioActivo= "UsuarioActivo";


    public static int agregarUsuarioActivo(Context context, UsuarioActivo usuario) {
        Base_Datos bd = new Base_Datos(context);
        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            ContentValues registro = new ContentValues();
            registro.put("User", usuario.getUser());
            registro.put("Token", usuario.getToken());
            db.insert(SQLITE.tablaUsuarioActivo, null, registro);
            db.close();
            return 1;
        }
        return 2;
    }




    public static ArrayList<UsuarioActivo> obtenerUsuarioActivo(Context contexto) {
        Base_Datos base_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_datos.getWritableDatabase();
        if (db != null) {
            ArrayList<UsuarioActivo> lista = new ArrayList<>();
            Cursor c = db.rawQuery("select * from " + tablaUsuarioActivo + ";", null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        lista.add(new UsuarioActivo(c.getString(0),c.getString(1)));
                    } while (c.moveToNext());
                }
            }
            return lista;
        }
        return null;
    }

    public static void limpiarTabla(Context contexto, String tabla) {
        Base_Datos bd = new Base_Datos(contexto);
        SQLiteDatabase db = bd.getWritableDatabase();
        db.execSQL("delete  from " + tabla + ";");
        db.close();
    }








}
