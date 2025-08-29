package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "descuentos")
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDescuento;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private double porcentajeDescuento;
}
