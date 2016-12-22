package com.uagrm.emprendecruz.easymarket.utils;

/**
 * Created by Cosio on 19/10/2016.
 */
public class Detalle {
    private String descripcion;
    private String precio;
    private String cantidad;

    public Detalle(String descripcion, String precio, String cantidad) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
