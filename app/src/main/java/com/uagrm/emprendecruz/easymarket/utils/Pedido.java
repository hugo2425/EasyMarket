package com.uagrm.emprendecruz.easymarket.utils;

/**
 * Created by Cosio on 17/10/2016.
 */
public class Pedido {

    private String idPedido;
    private String latitud;
    private String longitud;
    private String monto;
    private String fecha;
    private String idCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String imgCli;

    public Pedido(){}

    public Pedido(String idPedido, String latitud, String longitud, String monto, String fecha, String idCliente, String nombre, String telefono, String direccion, String imgCli) {
        this.idPedido = idPedido;
        this.latitud = latitud;
        this.longitud = longitud;
        this.monto = monto;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.imgCli = imgCli;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImgCli() {
        return imgCli;
    }

    public void setImgCli(String imgCli) {
        this.imgCli = imgCli;
    }
}
