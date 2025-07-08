package com.perfulandia.carritoservice.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "Entidad que representa un Item dentro del Carrito")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CarritoItem {
    @Schema (description = "ID autogenerado con IDENTITY", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema (description = "ID del producto", example = "1")
    private long productoId;

    @Schema (description = "Nombre del producto", example = "Cloud de Ariana Grande")
    private String nombre;

    @Schema (description = "Precio del producto", example = "10000")
    @Column(nullable = false)
    private double precio;

    @Schema (description = "Cantidad el producto", example = "2")
    private int cantidad;

    @Schema (description = "Precio total del item considerando el precio del producto y su cantidad", example = "20000")
    private double precioTotal;

    @Schema (description = "ID del carrito al que el item es agregado", example = "3")
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    @JsonBackReference //este no a ser serializado-hijo
    private Carrito carrito;

    //calcula el precio total multiplicando la cantidad del producto x el precio unitario
    public void calcularPrecioTotal() {
        this.precioTotal = this.precio * this.cantidad;
    }

}
