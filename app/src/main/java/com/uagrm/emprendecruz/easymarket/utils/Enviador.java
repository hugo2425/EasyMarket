package com.uagrm.emprendecruz.easymarket.utils;

/**
 * Created by Cosio on 20/10/2016.
 */
public class Enviador {
    private String idEnviador;
    private String nombre;
    private String imgEnv;
    private String correo;
    private String contrasena;

    public Enviador(){}

    public Enviador(String idEnviador, String nombre, String imgEnv, String correo, String contrasena) {
        this.idEnviador = idEnviador;
        this.nombre = nombre;
        this.imgEnv = imgEnv;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getIdEnviador() {
        return idEnviador;
    }

    public void setIdEnviador(String idEnviador) {
        this.idEnviador = idEnviador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImgEnv() {
        return imgEnv;
    }

    public void setImgEnv(String imgEnv) {
        this.imgEnv = imgEnv;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
