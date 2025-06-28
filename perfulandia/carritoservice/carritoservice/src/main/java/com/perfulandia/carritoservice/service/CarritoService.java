package com.perfulandia.carritoservice.service;
import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.model.ProductoDTO;
import org.springframework.stereotype.Service;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//listar-guardar-buscar-eliminar-actualizar

@Service
public class CarritoService {
    //repositorio
    public final CarritoRepository repo;
    public CarritoService(CarritoRepository repo) {
        this.repo = repo;
    }
    private final RestTemplate restTemplate = new RestTemplate();


    //listar todos los carritos
    public List<Carrito> listar() {return repo.findAll();}

    //guardar un carrito
    public Carrito guardar(List<ProductoDTO> productoDTOS) {
        Carrito carrito = new Carrito();
        List<CarritoItem> items = new ArrayList<>();
        for (ProductoDTO productoDTO : productoDTOS) {
            ProductoDTO producto = restTemplate.getForObject(
                    "http://localhost:8082/perfulandia_productos" + productoDTO.getProductoId(), // example URL
                    ProductoDTO.class
            );
            CarritoItem item = CarritoItem.builder()
                    .productoId(productoDTO.getProductoId())
                    .nombre(producto.getNombre())
                    .precio(producto.getPrecio())
                    .cantidad(productoDTO.getCantidad())
                    .carrito(carrito)
                    .build();
            item.calcularPrecioTotal();
            items.add(item);
        }
        carrito.setItems(items);
        return repo.save(carrito);
    }

    //buscar
    public Carrito buscarPorId(long id) {return repo.findById(id).orElse(null);}

    //eliminar
    public void eliminar(long id) {repo.deleteById(id);}

    //actualizar productos
    public Carrito actualizar(long carritoId, List<CarritoItem> nuevosProductos) {
        Carrito carrito = repo.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito noexiste"));

        // For example, just replace the product list with the new one:
        carrito.setItems(nuevosProductos);

        return repo.save(carrito);
    }




}
