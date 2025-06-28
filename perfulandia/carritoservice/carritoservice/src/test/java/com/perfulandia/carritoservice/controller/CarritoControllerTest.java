package com.perfulandia.carritoservice.controller;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.service.CarritoService;

//1 Importaciones de JUnit 5
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//2 Anotación para probar solo el controlador (no el contexto completo de Spring Boot)
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//3 Anotación para simular un bean dentro del ApplicationContext de Spring
import org.springframework.test.context.bean.override.mockito.MockitoBean;

//4 Inyección automática del cliente de pruebas web
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

//5 Métodos estáticos de Mockito
import static org.mockito.Mockito.*;

//6 Métodos para construir peticiones HTTP simuladas y verificar respuestas
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//7 Librería para convertir objetos a JSON (necesaria en peticiones POST)
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
//Anotación que indica que solo probará el carritoController
@WebMvcTest(CarritoController.class)

public class CarritoControllerTest {

    //Cliente hhtp para poder realizar pruebas unitarias injectar
    @Autowired
    private MockMvc mockMvc;

    //Simulación  del servicio para evitar acceder a datos reales
    @MockitoBean
    private CarritoService service;

    //convertimos texto a JSON y viceversa
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("TEST CONTROLLER 1 - GET LISTA")
    void testGetAll() throws Exception {
        //creamos carrito con id
        Carrito carrito = new Carrito();
        carrito.setId(1l);

        //creamos item1 (un carrito Item)
        CarritoItem item1 = new CarritoItem();
        item1.setId(1l);
        item1.setProductoId(1234L);
        item1.setNombre("PERFUME BACAN");
        item1.setPrecio(1000);
        item1.setCantidad(2);
        item1.setPrecioTotal(2000);
        //el carrito es el q creamos arriba
        item1.setCarrito(carrito);

        //inicializamos la lista vacia
        List<CarritoItem> items = new ArrayList<>();
        //y a esta lista vacia le agregamose el item1 que hicimos arriba
        items.add(item1);

        //retorna el carrito 1L con items (la lista de items a la q le agregamos el item1)
        when(service.listar()).thenReturn(List.of(new Carrito(1L,items)));
        mockMvc.perform(get("/api/perfulandia_carritos"))
                //3 lo que esperamos en esa petición
                .andExpect(status().isOk())//código 200
                //4 verificamos que el primer elemento JSON de la lista de items sea PERFUME BACAN
                .andExpect(jsonPath("$[0].items[0].nombre").value("PERFUME BACAN"));
    }
}
