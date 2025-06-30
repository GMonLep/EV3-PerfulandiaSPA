package com.perfulandia.usuarioservice.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        //configuracion d swagger
        return new OpenAPI()
                .info(new Info()
                        .title("usuarioservice 😊")
                        .version("1.0")
                        .description("Microservicio de Usuario API")
                        .contact(new Contact()
                                .name("Génesis Montero")
                                .email("gen.montero@duocuc.cl")
                                .url("https://www.duoc.cl")
                        ));


    }
}
