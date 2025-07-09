package com.perfulandia.usuarioservice.assembler;
import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.controller.UsuarioController;


import org.springframework.hateoas.EntityModel; //represenando recurso con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; //convierte un modelo en hateoas
import org.springframework.stereotype.Component; //marca como componente de spring

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,

                linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withSelfRel(),

                linkTo(methodOn(UsuarioController.class).listar()).withRel("lista de usuarios"),

                linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(),new HashMap<>())).withRel("actualizar usuario"),
 
                linkTo(methodOn(UsuarioController.class).guardar(null)).withRel("guardar usuario"),

                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getId())).withRel("eliminar usuario"));

    }

}
