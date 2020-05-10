package com.c_felix.pocketmarket.Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Base_Datos extends SQLiteOpenHelper {
    public static final String nombreBaseDatos = "AgroNodo";
    public static final int Version = 1;
    Context context;


    public Base_Datos(@Nullable Context context) {
        super(context, nombreBaseDatos, null, Version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
