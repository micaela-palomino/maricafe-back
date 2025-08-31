package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(unique = true, nullable = false)
    private String nombre;
}
