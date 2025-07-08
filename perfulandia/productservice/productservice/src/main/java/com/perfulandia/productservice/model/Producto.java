package com.perfulandia.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "Entidad que representa un Producto")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Schema (description = "ID autogenerado con IDENTITY", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productoId;

    @Schema (description = "Nombre del producto", example = "Cloud de Ariana Grande")
    private String nombre;

    @Schema (description = "Precio del producto", example = "30000")
    private double precio;

    @Schema (description = "Stock disponible del producto", example = "4")
    private int stock;

}

