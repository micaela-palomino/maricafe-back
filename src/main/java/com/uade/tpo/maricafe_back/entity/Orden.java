package com.uade.tpo.maricafe_back.entity;
import java.util.List;

public class Orden {
    private int idOrden;
    private String fechaOrden;
    private List<Producto> productos;
    private double precioTotal;
}
