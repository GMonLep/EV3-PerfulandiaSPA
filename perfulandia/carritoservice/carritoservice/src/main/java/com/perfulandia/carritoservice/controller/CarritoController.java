package com.perfulandia.carritoservice.controller;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.model.ProductoDTO;
import com.perfulandia.carritoservice.service.CarritoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Tag(name="Carritos", description = "Operaciones CRUD para el microservicio de carrito")
@RestController
@RequestMapping("/api/carritos")

public class CarritoController {
    private final CarritoService servicio;
    private final RestTemplate restTemplate;

    public CarritoController(CarritoService servicio, RestTemplate restTemplate) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
    }

    @Operation(summary="Obtener todos los carritos de la base de datos",description = "Devuelve todos los carritos almacenados en la base de datos")
    @ApiResponse(responseCode = "200",description = "consulta exitosa")
    @GetMapping
    public List<Carrito> getAll(){
        return servicio.listar();
    }


    @Operation(summary="Buscar carrito por ID",description = "Busca al carrito por id en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Se encontró al carrito"),
            @ApiResponse(responseCode = "402",description = "No se encontró al carrito")
    })
    @GetMapping("/{id}")
    public Carrito getById(@PathVariable int id){return servicio.buscarPorId(id);}

    @Operation(summary="Crear un nuevo carrito",description = "Agrega un nuevo carrito a la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Creacion exitosa"),
            @ApiResponse(responseCode = "400",description = "Error validacion")
    })
    @PostMapping
    public Carrito guardarCarrito(@RequestBody List<ProductoDTO> items) {return servicio.guardar(items);}


    @Operation(summary="Eliminar carrito",description = "Elimina un carrito de la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "404",description = "Carrito a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        servicio.eliminar(id);
    }


    @Operation(summary="Actualizar carrito",description = "Actualiza los datos de un carrito en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400",description = "Datos proporcionados son inválidos")
    })
    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable int id, @RequestBody List<CarritoItem> items) {return servicio.actualizar(id,items);}

}