package org.github.dabson10.ecomerce.productos;

import jakarta.validation.Valid;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
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

    /**
     * Endpoint para crear un producto.
     * @param producto
     * @return : Regresará un objeto con datos fundamentales del producto.
     */
    @PostMapping("/create")
    public ResponseEntity<ProductoSimpleDTO> crearProducto(
            @RequestBody @Valid ProductoCreateDTO producto
    ){
        ProductoSimpleDTO product = proSe.crearProductoSimple(producto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("/delete/{ID}")
    public ResponseEntity<Map<String, String>> eliminarProducto(
            @PathVariable @Validated UUID ID
            ){
        proSe.eliminarProducto(ID);
        return new ResponseEntity<>(Map.of("Acción:","Producto eliminado."), HttpStatus.NO_CONTENT);
    }
}
