package com.perfulandia.carritoservice.controller;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfulandia_carritos")

public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Carrito> getAll(){
        return service.listar();
    }
}
