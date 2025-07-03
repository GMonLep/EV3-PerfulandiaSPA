package com.perfulandia.productservice.config;
import com.perfulandia.productservice.repository.ProductoRepository;
import com.perfulandia.productservice.model.Producto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Creacion {
    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            //iniciamos la cuenta vacia pq la base de datos va a estar vacia
            if (repository.count() == 0) {
                repository.save(Producto.builder()
                        .nombre("Armando Armani")
                        .precio(1000)
                        .stock(5)
                        .build());

                repository.save(Producto.builder()
                        .nombre("Cloud 5")
                        .precio(2000)
                        .stock(2)
                        .build());

                repository.save(Producto.builder()
                        .nombre("Perfume buenisimo")
                        .precio(3000)
                        .stock(4)
                        .build());

                System.out.println("Productos creados");
            } else {
                System.out.println("Productos ya existen. No se agregaron");
            }
        };
}}
