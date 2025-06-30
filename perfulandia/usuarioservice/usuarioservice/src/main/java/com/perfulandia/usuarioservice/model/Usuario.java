package com.perfulandia.usuarioservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
@Schema (description = "Entidad que representa un Usuario")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //Crear objetos de manera flexible = Constructor Flex
public class Usuario {
    @Schema (description = "ID autogenerado con IDENTITY", example = "1")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Schema (description = "Nombre del usuario", example = "Juan Perez")
    private String nombre;

    @Schema (description = "Correo del usuario", example = "juanchito@gmail.com")
    private String correo;

    @Schema (description = "Rol del usuario", example = "Administrador")
    private String rol; // ADMIN, GERENTE, Usuario
}
