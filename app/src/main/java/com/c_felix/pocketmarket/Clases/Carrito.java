package com.c_felix.pocketmarket.Clases;

public class Carrito {
    int ID;
    int ID_Producto;
    int ID_Usuario;
    int Cantidad;

    public Carrito(int ID, int ID_Producto, int ID_Usuario,int cantidad) {
        this.ID = ID;
        this.ID_Producto = ID_Producto;
        this.ID_Usuario = ID_Usuario;
        this.Cantidad = cantidad;
    }

    public Carrito() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Producto() {
        return ID_Producto;
    }

    public void setID_Producto(int ID_Producto) {
        this.ID_Producto = ID_Producto;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        this.Cantidad = cantidad;
    }
}
