package com.perfulandia.productservice.controller;
import com.perfulandia.productservice.assembler.ProductoAssembler;
import com.perfulandia.productservice.model.ProductoDTO;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService servicio;
    private final ProductoAssembler assembler;
    private final RestTemplate restTemplate;

    public ProductoController(ProductoService servicio, ProductoAssembler assembler, RestTemplate restTemplate) {
        this.servicio = servicio;
        this.assembler = assembler;
        this.restTemplate = restTemplate;
    }


    //listar
    @GetMapping
    public CollectionModel<EntityModel<Producto>> listar(){
        List<EntityModel<Producto>> productos = servicio.listar().stream()
                .map(assembler::toModel) //transformando cada carrito en un modelo d entidad<Carrito> con enlaces
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel()//enlace a si mismo
        );
    }
    //guardar
    @PostMapping
    public EntityModel<Producto> guardar(@RequestBody Producto producto){
        return assembler.toModel(servicio.guardar(producto));
    }

    //buscar x id
    @GetMapping("/buscar/{id}")
    public EntityModel<Producto> buscar(@PathVariable long id){
        return assembler.toModel(servicio.bucarPorId(id));
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public RepresentationModel<?> eliminar(@PathVariable long id) {
        Producto producto = new Producto();

        servicio.eliminar(id);
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(ProductoController.class).listar()).withRel("eliminar producto"));
        model.add(linkTo(methodOn(ProductoController.class).listar()).withRel("lista de productos"));
        model.add(linkTo(methodOn(ProductoController.class).actualizar(producto.getProductoId(),new HashMap<>())).withRel("actualizar producto"));
        model.add(linkTo(methodOn(ProductoController.class).guardar(null)).withRel("guardar producto"));
        return model;
    }

    //Nuevo método
    @GetMapping("/usuario/{id}")
    public EntityModel<Usuario> obtenerUsuario(@PathVariable long id){
        Usuario usuario = restTemplate.getForObject("http://localhost:8081/api/usuarios/" + id, Usuario.class);
        EntityModel<Usuario> model = EntityModel.of(usuario);

        model.add(linkTo(methodOn(ProductoController.class).obtenerUsuario(id)).withSelfRel());

        model.add(linkTo(methodOn(ProductoController.class).listar()).withRel("lista de productos"));
        model.add(linkTo(methodOn(ProductoController.class).guardar(null)).withRel("guardar producto"));

        return model;
    }



    @PatchMapping("/{id}")
    public EntityModel<Producto> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return assembler.toModel(servicio.actualizar(id, campos));
    }

    //get pal dto
    @GetMapping("/producto/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> getProducto(@PathVariable Long id) {
        ProductoDTO producto = servicio.getProductoDTOById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<ProductoDTO> model = EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProducto(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("lista-productos"),
                linkTo(methodOn(ProductoController.class).actualizar(id, new HashMap<>())).withRel("actualizar-producto"),
                linkTo(methodOn(ProductoController.class).eliminar(id)).withRel("eliminar-producto")
        );

        return ResponseEntity.ok(model);
    }


}
