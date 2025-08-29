package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
    @JsonProperty("nombre_usuario")
    private String nombreUsuario;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("apellido")
    private String apellido;
    @JsonProperty("contraseña")
    private String contraseña;
    @JsonProperty("mail")
    private String mail;

}
