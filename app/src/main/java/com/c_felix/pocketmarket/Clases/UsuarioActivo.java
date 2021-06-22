package com.c_felix.pocketmarket.Clases;

public class UsuarioActivo {
    String user;
    String token;

    public UsuarioActivo(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public UsuarioActivo() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
