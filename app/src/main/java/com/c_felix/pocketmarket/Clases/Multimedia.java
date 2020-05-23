package com.c_felix.pocketmarket.Clases;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Multimedia {
    int ID, id_usuario;
    Bitmap imagen;

    public Multimedia(int ID, int id_usuario, Bitmap imagen) {
        this.ID = ID;
        this.id_usuario = id_usuario;
        this.imagen = imagen;
    }

    public Multimedia() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
