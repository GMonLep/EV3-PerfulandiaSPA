package com.perfulandia.productservice.model;

//dto para carrito

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Objeto de transferencia de datos para entidad producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    @Schema (description = "ID del producto correspondiente", example = "1")
    private long productoId;

    @Schema (description = "Nombre del producto", example = "Cloud de Ariana Grande")
    private String nombre;

    @Schema (description = "Cantidad el producto", example = "9")
    private int cantidad;

    @Schema (description = "Precio del producto", example = "30000")
    private double precio;


}
