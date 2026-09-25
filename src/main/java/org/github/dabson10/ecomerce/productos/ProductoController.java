package org.github.dabson10.ecomerce.productos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.productos.dto.ProductoCategoriaDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCompletoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductoController {
    private final ProductoService proSe;

    public ProductoController(ProductoService proSe){
        this.proSe = proSe;
    }

    @GetMapping("/get/{ID}")
    public ResponseEntity<ProductoMostrarDTO> traerUnProducto(
            @PathVariable(name = "ID") UUID ID
    ){
        ProductoMostrarDTO producto = proSe.mostrarProducto(ID);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }
    /**
     * Endpoint para crear un producto.
     * @param producto
     * @return : Regresará un objeto con datos fundamentales del producto.
     */
    @PostMapping("/create")
    public ResponseEntity<ProductoCompletoDTO> crearProducto(
            @RequestBody @Valid ProductoCreateDTO producto
    ){
        ProductoCompletoDTO product = proSe.crearProductoSimple(producto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("/categories/update")
    public ResponseEntity<ProductoCompletoDTO> agregarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productoCat
    ){
        ProductoCompletoDTO producto = proSe.agregarCategorias(productoCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/categories/delete")
    public ResponseEntity<ProductoCompletoDTO> eliminarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productCat
    ){
        ProductoCompletoDTO producto = proSe.eliminarCategorias(productCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }


    @PatchMapping("/{ID}")
    public ResponseEntity<ProductoCompletoDTO> cambiarStock(
            @PathVariable @NotNull(message = "Ingrese un ID") UUID ID,
            @RequestParam(name = "stock")
            @PositiveOrZero(message = "Ingrese una cantidad positiva")
            @NotNull(message = "Ingrese una cantidad.") Integer stockNew
    ){
        ProductoCompletoDTO producto = proSe.cambiarStock(ID, stockNew);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/delete/{ID}")
    public ResponseEntity<Map<String, String>> eliminarProducto(
            @PathVariable @Validated UUID ID
            ){
        proSe.eliminarProducto(ID);
        return new ResponseEntity<>(Map.of("Acción:","Producto eliminado."), HttpStatus.NO_CONTENT);
    }
}
