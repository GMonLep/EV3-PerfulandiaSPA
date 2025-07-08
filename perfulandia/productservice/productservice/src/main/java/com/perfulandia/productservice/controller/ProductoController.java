package com.perfulandia.productservice.controller;
import com.perfulandia.productservice.model.ProductoDTO;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

//Nuevas importaciones DTO conexión al MS usuario
import org.springframework.web.client.RestTemplate;
//Para hacer peticiones HTTP a otros microservicios.

@Tag(name="Productos", description = "Operaciones CRUD para el microservicio de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {



    private final ProductoService servicio;
    private final RestTemplate restTemplate;
    public ProductoController(ProductoService servicio,  RestTemplate restTemplate){
        this.servicio = servicio;
        this.restTemplate = restTemplate;
    }


    @Operation(summary="Obtener todos los productos de la base de datos",description = "Devuelve todos los productos almacenados en la base de datos")
    @ApiResponse(responseCode = "200",description = "consulta exitosa")
    @GetMapping
    public List<Producto> listar(){
        return servicio.listar();
    }

    @Operation(summary="Crear un nuevo producto",description = "Agrega un nuevo producto a la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Creacion exitosa"),
            @ApiResponse(responseCode = "400",description = "Error validacion")
    })
    @PostMapping
    public Producto guardar(@RequestBody Producto producto){
        return servicio.guardar(producto);
    }

    @Operation(summary="Buscar producto por ID",description = "Busca al producto por id en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Se encontró al producto"),
            @ApiResponse(responseCode = "402",description = "No se encontró al producto")
    })
    @GetMapping("/buscar/{id}")
    public Producto buscar(@PathVariable long id){
        return servicio.bucarPorId(id);
    }



    @Operation(summary="Eliminar producto",description = "Elimina un producto de la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "404",description = "Producto a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        servicio.eliminar(id);
    }

    @Operation(summary="Buscar usuario por ID",description = "Busca al usuario por id en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Se encontró al usuario"),
            @ApiResponse(responseCode = "402",description = "No se encontró al usuario")
    })
    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id){
        return restTemplate.getForObject("http://localhost:8081/api/usuarios/"+id,Usuario.class);
    }

    @Operation(summary="Actualizar producto",description = "Actualiza los datos de un producto en la base de datos")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400",description = "Datos proporcionados son inválidos")
    })
    @PatchMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return servicio.actualizar(id,campos);
    }

    @Operation(summary="Busca a un producto",description = "Busca un producto en la base de datos y si existe lo convierte en un DTO para integrarlo con el carrito cuando este lo llame")
    @ApiResponses({ @ApiResponse(responseCode = "200",description = "Producto encontrado"),
            @ApiResponse(responseCode = "404",description = "Producto no existe")
    })
    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        ProductoDTO producto = servicio.getProductoDTOById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }
}
