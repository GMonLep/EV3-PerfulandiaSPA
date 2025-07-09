package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import com.perfulandia.usuarioservice.service.UsuarioService;
import com.perfulandia.usuarioservice.assembler.UsuarioAssembler;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioAssembler assembler;

    public UsuarioController(UsuarioService service, UsuarioAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Usuario>> listar(){
        List<EntityModel<Usuario>> usuarios = service.listar().stream()
                .map(assembler::toModel) //transformando cada carrito en un modelo d entidad<Carrito> con enlaces
                .collect(Collectors.toList());
        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel()//enlace a si mismo
        );
    }

    @PostMapping
    public EntityModel<Usuario> guardar(@RequestBody Usuario usuario){
        return assembler.toModel(service.guardar(usuario));
    }


    @GetMapping("/{id}")
    public EntityModel<Usuario> buscar(@PathVariable long id){
        return assembler.toModel(service.buscar(id));
    }

    @DeleteMapping("/{id}")
    public RepresentationModel<?> eliminar(@PathVariable long id) {
        service.eliminar(id);
        Usuario usuario = new Usuario();
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(UsuarioController.class).eliminar(id)).withSelfRel());
        model.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("lista de usuarios"));
        model.add(linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(),new HashMap<>())).withRel("actualizar usuario"));
        model.add(linkTo(methodOn(UsuarioController.class).guardar(null)).withRel("guardar usuario"));
        model.add(linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withRel("buscar usuario"));
        return model;
    }

    @PatchMapping("/{id}") //Este mérecibe un ID desde la URL y un cuerpo con los campos a actualizar (en formato JSON)
    public EntityModel<Usuario> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return assembler.toModel(service.actualizar(id, campos));
    }


}
