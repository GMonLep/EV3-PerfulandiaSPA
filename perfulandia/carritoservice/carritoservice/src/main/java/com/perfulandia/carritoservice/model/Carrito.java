package com.perfulandia.carritoservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Entidad que representa un Carrito")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carrito {
    @Schema (description = "ID autogenerado con IDENTITY", example = "1")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Schema (description = "Lista de items asociadas al carrito, cada item siendo un objeto de CarritoItem",
            example = "[{1,1,Cloud 9, 2000,2,4000},{2,3,Perfumecito, 4000,1,4000}]")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "carrito")
    @JsonManagedReference //este campo si va a ser serializado-padre
    private List<CarritoItem> items = new ArrayList<>();

}
