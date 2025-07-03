package com.perfulandia.carritoservice.assembler;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.controller.CarritoController;

import org.springframework.hateoas.EntityModel; //represenando recurso con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; //convierte un modelo en hateoas
import org.springframework.stereotype.Component; //marca como componente de spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component //clase que transforma Carrito en un EntityModel
public class CarritoAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                //enlace a carrito actual
                linkTo(methodOn(CarritoController.class).getById(carrito.getId())).withSelfRel(),
                //lista completa
                linkTo(methodOn(CarritoController.class).getAll()).withRel("lista de carritos"),
                //para actualizare
                linkTo(methodOn(CarritoController.class).actualizarCarrito(carrito.getId(), carrito.getItems())).withRel("actualizar carrito"),
                // crear carrito
                linkTo(methodOn(CarritoController.class).guardarCarrito(null)).withRel("guardar carrito"),
                //eliminar carrito
                linkTo(methodOn(CarritoController.class).eliminar(carrito.getId())).withRel("eliminar carrito"));

    }


}
