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


    public static int obtenerValorMaximo(Context contexto, String tabla, String campo) {
        Base_Datos bd = new Base_Datos(contexto);
        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select MAX(" + campo + ") from " + tabla + " ;", null);
            if (c.getCount() == 1) {
                c.moveToFirst();
                int valor = c.getInt(0);
                db.close();
                return valor;
            }
        }
        return 1;
    }
    public static int obtenerTamañoTabla(Context contexto, String tabla) {
        Base_Datos base_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_datos.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from " + tabla + ";", null);
            int contador = c.getCount();
            db.close();
            return contador;
        }
        return 0;
    }

    public static Bitmap obtenerImagen(Context contexto, int ID) {
        if (obtenerTamañoTabla(contexto, tablaUsuarioActivo) == 1) {
            Base_Datos base_de_datos = new Base_Datos(contexto);
            SQLiteDatabase db = base_de_datos.getWritableDatabase();
            if (db != null) {
                Cursor c = db.rawQuery("select * from " + tablaUsuarioActivo +" where ID = "+ ID+ " ;", null);
                if (c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        Bitmap imagen = BitmapFactory.decodeByteArray(c.getBlob(9), 0, c.getBlob(9).length);
                        db.close();
                        return imagen;
                    }
                }
            }
        }
        return null;
    }



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



  /*  public static Usuarios obtenerUsuario(Context contexto, int ID) {
        Base_Datos base_de_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_de_datos.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from " + tablaUsuarios+ " where ID= "+ ID + ";", null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    Usuarios usuario = new Usuarios(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),BitmapFactory.decodeByteArray(c.getBlob(9), 0, c.getBlob(9).length));
                    db.close();
                    return usuario;
                }
            }
        }
        return null;
    }
*/

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
