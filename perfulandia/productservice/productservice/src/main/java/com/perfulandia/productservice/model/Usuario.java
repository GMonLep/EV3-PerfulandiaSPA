package com.perfulandia.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Objeto de transferencia de datos para entidad usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Schema (description = "ID del usuario correspondiente", example = "1")
    private long id;

    @Schema (description = "Datos del usuario", example = "Juan Perez, juanchito@gmail.com, Administrador")
    private String nombre,correo, rol;
}
