package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import com.perfulandia.usuarioservice.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.perfulandia.usuarioservice.model.Usuario;
import java.util.List;
import java.util.Map;

@Tag(name="Usuarios", description = "Operaciones CRUD para el microservicio de usuarios")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    //Constructor para poder consumir la interfaz
    public UsuarioController(UsuarioService service){
        this.service=service;
    }

    @Operation(summary="Obtener todos los usuarios de la base de datos",description = "Devuelve todos los usuario almacenados en la base de datos")
    @ApiResponse(responseCode = "200",description = "consulta exitosa")
    @GetMapping
    public List<Usuario> listar(){
        return service.listar();
    }


    @Operation(summary="Crear un nuevo usuario",description = "Agrega un nuevo usuario a la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Creacion exitosa"),
                    @ApiResponse(responseCode = "400",description = "Error validacion")
            })
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario){
        return service.guardar(usuario);
    }


    @Operation(summary="Buscar usuario por ID",description = "Busca al usuario por id en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Se encontró al usuario"),
            @ApiResponse(responseCode = "402",description = "No se encontró al usuario")
    })
    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable long id){
        return service.buscar(id);
    }


    @Operation(summary="Eliminar usuario",description = "Elimina un usuario de la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "404",description = "Usuario a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){service.eliminar(id);
    }

    @Operation(summary="Actualizar usuario",description = "Actualiza los datos de un usuario en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400",description = "Datos proporcionados son inválidos")
    })
    @PatchMapping("/{id}") //Este mérecibe un ID desde la URL y un cuerpo con los campos a actualizar (en formato JSON)
    public Usuario actualizar(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return service.actualizar(id,campos);
    }


}
