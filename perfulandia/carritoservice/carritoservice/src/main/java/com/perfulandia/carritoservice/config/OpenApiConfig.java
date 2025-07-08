package com.perfulandia.carritoservice.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("carritomicroservice 🛒")
                        .version("1.0")
                        .description("Microservicio de Carrito")
                        .contact(new Contact()
                                .name("Génesis Montero")
                                .email("gen.montero@duocuc.cl")
                                .url("https://www.duoc.cl")
                        ));


    }
}
