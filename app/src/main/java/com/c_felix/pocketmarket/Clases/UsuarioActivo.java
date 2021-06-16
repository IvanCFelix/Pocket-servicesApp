package com.c_felix.pocketmarket.Clases;

public class UsuarioActivo {
    String username;
    int ID;

    public UsuarioActivo(int ID,String username) {
        this.username = username;
        this.ID = ID;
    }

    public UsuarioActivo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
