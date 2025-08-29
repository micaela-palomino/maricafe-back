package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String titulo;
    private String descripcion;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private String metadata;
    private int stock;
}
