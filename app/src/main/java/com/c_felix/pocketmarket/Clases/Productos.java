package com.c_felix.pocketmarket.Clases;

public class Productos {
    String Titulo,Descripcion,UnidadMedida,Categoria;
    int ID,ID_usuario,inventario;
    double PrecioUnidad;

    public Productos(String titulo, String descripcion, String unidadMedida, String categoria, int ID, int ID_usuario, int inventario, double precioUnidad) {
        Titulo = titulo;
        Descripcion = descripcion;
        UnidadMedida = unidadMedida;
        Categoria = categoria;
        this.ID = ID;
        this.ID_usuario = ID_usuario;
        this.inventario = inventario;
        this.PrecioUnidad = precioUnidad;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        UnidadMedida = unidadMedida;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public double getPrecioUnidad() {
        return PrecioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.PrecioUnidad = precioUnidad;
    }
}
