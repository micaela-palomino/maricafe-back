package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String contraseña;
    private String mail;

    @Enumerated(EnumType.STRING)
    private TipoRol tipoRol;
}
