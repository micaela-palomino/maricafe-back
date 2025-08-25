package com.uade.tpo.maricafe_back.entity;

public class Producto {
    private int idProducto;
    private String titulo;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private String metadata; //asumo que es info adicional que no es tan importante y se puede guardar aca asi que va como string
    private int stock;

    public void validarStock() {
        // Tomi hizo esto. Hay que darle amor
    }
    public void crearProducto() {
        // Tomi hizo esto. Hay que darle amor
    }
    public void modificarProducto() {
        // Tomi hizo esto. Hay que darle amor

    }    public void eliminarProducto() {
        // Tomi hizo esto. Hay que darle amor
    }
}
