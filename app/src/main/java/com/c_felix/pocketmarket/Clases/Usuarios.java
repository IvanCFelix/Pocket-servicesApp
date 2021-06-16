package com.c_felix.pocketmarket.Clases;

import android.graphics.Bitmap;

public class Usuarios {
    int  ID;
    String nombre,nombreEmpresa, username, correo,contraseña,TipoUsuario;
    String  numeroTel;
    String ubicacion;
    Bitmap imagen;

    public Usuarios(int ID, String nombre, String nombreEmpresa, String username, String correo, String contraseña, String tipoUsuario, String numeroTel, String ubicacion, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.nombreEmpresa = nombreEmpresa;
        this.username = username;
        this.correo = correo;
        this.contraseña = contraseña;
        TipoUsuario = tipoUsuario;
        this.numeroTel = numeroTel;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public Usuarios() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
