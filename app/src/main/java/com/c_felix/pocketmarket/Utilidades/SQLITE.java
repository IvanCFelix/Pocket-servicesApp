package com.c_felix.pocketmarket.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import com.c_felix.pocketmarket.Clases.Productos;
import com.c_felix.pocketmarket.Clases.UsuarioActivo;
import com.c_felix.pocketmarket.Clases.Usuarios;

import java.util.ArrayList;

public class SQLITE {
    public static final String tablaUsuarios = "Usuarios";
    public static final String tablaUsuarioActivo= "UsuarioActivo";
    public static final String tablaRepartidores = "Repartidores";


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

    public static int agregarUsuario(Context context, Usuarios usuario, String formato_imagen) {
        Base_Datos bd = new Base_Datos(context);
        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            ContentValues registro = new ContentValues();
            registro.put("ID", usuario.getID());
            registro.put("Nombre", usuario.getNombre());
            registro.put("Empresa", usuario.getNombreEmpresa());
            registro.put("Username", usuario.getUsername());
            registro.put("Correo",usuario.getCorreo());
            registro.put("Contraseña", usuario.getContraseña());
            registro.put("TipoUsuario", usuario.getTipoUsuario());
            registro.put("NumeroTel", usuario.getNumeroTel());
            registro.put("Ubicacion",usuario.getUbicacion());
            registro.put("Imagen", Metodos_Estaticos.imagenArrayBytes(usuario.getImagen(), formato_imagen));
            db.insert(SQLITE.tablaUsuarios, null, registro);
            db.close();
            return 1;
        }
        return 2;
    }

    public static int usuarioActivo(Context context, UsuarioActivo usuario) {
        Base_Datos bd = new Base_Datos(context);
        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            ContentValues registro = new ContentValues();
            registro.put("ID", usuario.getID());
            registro.put("Username", usuario.getUsername());
            db.insert(SQLITE.tablaUsuarioActivo, null, registro);
            db.close();
            return 1;
        }
        return 2;
    }


    public static Usuarios obtenerUsuarioUsername(Context contexto, String nombreUsuario) {
        Base_Datos base_de_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_de_datos.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from " + tablaUsuarios+ " where Username = '"+ nombreUsuario +"' ;", null);
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

    public static Usuarios obtenerUsuario(Context contexto, int ID) {
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


    public static ArrayList<Usuarios> obtenerUsuarios(Context contexto) {
        Base_Datos base_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_datos.getWritableDatabase();
        if (db != null) {
            ArrayList<Usuarios> lista = new ArrayList<>();
            Cursor c = db.rawQuery("select * from " + tablaUsuarios + ";", null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                    lista.add(new Usuarios(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),BitmapFactory.decodeByteArray(c.getBlob(9), 0, c.getBlob(9).length)));
                    } while (c.moveToNext());
                }
            }
            return lista;
        }
        return null;
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
                        lista.add(new UsuarioActivo(c.getInt(0),c.getString(1)));
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
        db.execSQL("delete from " + tabla + ";");
        db.close();
    }

    public static ArrayList<Productos> obtenerProductos(Context contexto) {
        Base_Datos base_datos = new Base_Datos(contexto);
        SQLiteDatabase db = base_datos.getWritableDatabase();
        if (db != null) {
            ArrayList<Productos> lista = new ArrayList<>();
            Cursor c = db.rawQuery("select * from " + tablaUsuarios + ";", null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                    //    lista.add(new Usuarios(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),BitmapFactory.decodeByteArray(c.getBlob(9), 0, c.getBlob(9).length)));
                    } while (c.moveToNext());
                }
            }
            return lista;
        }
        return null;
    }
}
