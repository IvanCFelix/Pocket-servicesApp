package com.c_felix.pocketmarket.Clases;

public class Pedidos {
    int ID, ID_Producto, ID_Usuario;
    int Cantidad;
    double a_Pagar;

    public Pedidos(int ID, int ID_Producto, int ID_Usuario, int cantidad, double a_Pagar) {
        this.ID = ID;
        this.ID_Producto = ID_Producto;
        this.ID_Usuario = ID_Usuario;
        Cantidad = cantidad;
        this.a_Pagar = a_Pagar;
    }

    public Pedidos() {
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

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public double getA_Pagar() {
        return a_Pagar;
    }

    public void setA_Pagar(double a_Pagar) {
        this.a_Pagar = a_Pagar;
    }
}