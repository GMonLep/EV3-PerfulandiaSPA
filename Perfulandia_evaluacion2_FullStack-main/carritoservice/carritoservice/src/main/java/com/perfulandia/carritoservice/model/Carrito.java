package com.perfulandia.carritoservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carrito {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    private List<CarritoItem> items = new ArrayList<>();

}
