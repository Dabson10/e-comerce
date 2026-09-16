package org.github.dabson10.ecomerce.productos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.productos.dto.ProductoCategoriaDTO;
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

    @PatchMapping("/categories/update")
    public ResponseEntity<ProductoSimpleDTO> agregarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productoCat
    ){
        ProductoSimpleDTO producto = proSe.agregarCategorias(productoCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/categories/delete")
    public ResponseEntity<ProductoSimpleDTO> eliminarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productCat
    ){
        ProductoSimpleDTO producto = proSe.eliminarCategorias(productCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }


    @PatchMapping("/{ID}")
    public ResponseEntity<ProductoSimpleDTO> cambiarStock(
            @PathVariable @NotNull(message = "Ingrese un ID") UUID ID,
            @RequestParam(name = "stock")
            @PositiveOrZero(message = "Ingrese una cantidad positiva")
            @NotNull(message = "Ingrese una cantidad.") Integer stockNew
    ){
        ProductoSimpleDTO producto = proSe.cambiarStock(ID, stockNew);
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
