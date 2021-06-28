package com.c_felix.pocketmarket.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Base_Datos extends SQLiteOpenHelper {
    public static final String nombreBaseDatos = "PocketServices";
    public static final int Version = 15;
    Context context;


    public Base_Datos(@Nullable Context context) {
        super(context, nombreBaseDatos, null, Version);
        this.context = context;
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SQLITE.tablaUsuarioActivo + "(User Text, Token text );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE.tablaUsuarioActivo);
        SharedPreferences pref = context.getSharedPreferences("intro", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        onCreate(db);
    }
}
