package com.perfulandia.productservice.assembler;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.model.ProductoDTO;
import com.perfulandia.productservice.controller.ProductoController;

import org.springframework.hateoas.EntityModel; //represenando recurso con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; //convierte un modelo en hateoas
import org.springframework.stereotype.Component; //marca como componente de spring

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                //enlace a carrito actual
                linkTo(methodOn(ProductoController.class).buscar(producto.getProductoId())).withSelfRel(),
                //lista completa
                linkTo(methodOn(ProductoController.class).listar()).withRel("lista de productos"),
                //para actualizare
                linkTo(methodOn(ProductoController.class).actualizar(producto.getProductoId(),new HashMap<>())).withRel("actualizar producto"),
                // crear carrito
                linkTo(methodOn(ProductoController.class).guardar(null)).withRel("guardar producto"),
                //eliminar carrito
                linkTo(methodOn(ProductoController.class).eliminar(producto.getProductoId())).withRel("eliminar producto"));

    }
}
