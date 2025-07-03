package com.perfulandia.carritoservice.controller;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.model.ProductoDTO;
import com.perfulandia.carritoservice.service.CarritoService;
import com.perfulandia.carritoservice.assembler.CarritoAssembler;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin("*")

public class CarritoController {
    private final CarritoService servicio;
    private final CarritoAssembler assembler;

    public CarritoController(CarritoService servicio, CarritoAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }


    //Listar los carritous
    @GetMapping
    public CollectionModel<EntityModel<Carrito>> getAll(){
        List<EntityModel<Carrito>> carritos = servicio.listar().stream()
                .map(assembler::toModel) //transformando cada carrito en un modelo d entidad<Carrito> con enlaces
                .collect(Collectors.toList());
        return CollectionModel.of(carritos,
                linkTo(methodOn(CarritoController.class).getAll()).withSelfRel()//enlace a si mismo
                );
    }


    @GetMapping("/{id}")
    public EntityModel<Carrito> getById(@PathVariable Long id){
        return assembler.toModel(servicio.buscarPorId(id));
    }

    //Guardar Carrito
    @PostMapping
    public EntityModel<Carrito> guardarCarrito(@RequestBody List<ProductoDTO> items){
        return assembler.toModel(servicio.guardar(items));
    }


    //Eliminar
    @DeleteMapping("/{id}")
    public RepresentationModel<?> eliminar(@PathVariable long id) {
        servicio.eliminar(id);
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(CarritoController.class).getAll()).withRel("eliminar carritos"));

        return model;
    }


    //Actualizar carritou
    @PutMapping("/{id}")
    public EntityModel<Carrito> actualizarCarrito(@PathVariable long id, @RequestBody List<CarritoItem> items){
        return assembler.toModel(servicio.actualizar(id, items));
    }

}