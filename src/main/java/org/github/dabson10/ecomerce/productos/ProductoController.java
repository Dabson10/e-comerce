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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
     * Controlador que trae un producto.
     * Es accesible por todos los roles.
     * @param ID : Id del producto.
     * @return : Regresará los datos del producto.
     */
    @GetMapping("/get/{ID}")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin', 'ROLE_Cliente')")
    public ResponseEntity<ProductoMostrarDTO> traerUnProducto(
            @PathVariable(name = "ID") UUID ID
    ){
        ProductoMostrarDTO producto = proSe.mostrarProducto(ID);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

    /**
     *Controlador para mostrar productos por una categoria
     * Es accesible por todos los roles.
     * @param categoria : Nombre de la categoria que se realizara una busqueda.
     * @return : Regresará una lista de productos que se mostraran.
     */
    @GetMapping("/get/list/{cat}")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin', 'ROLE_Cliente')")
    public ResponseEntity<List<ProductoMostrarDTO>> mostrarPorCategorias(
            @PathVariable(name = "cat") String categoria
    ){
        List<ProductoMostrarDTO> productos = proSe.productoCategorias(categoria);
        return new ResponseEntity<>(productos, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint para crear un producto.
     * Solo "Admin" y "Empresa" puede acceder a esta función.
     * @param producto : Datos del producto que se creara.
     * @return : Regresará un objeto con datos fundamentales del producto.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin')")
    public ResponseEntity<ProductoCompletoDTO> crearProducto(
            @RequestBody @Valid ProductoCreateDTO producto
    ){
        ProductoCompletoDTO product = proSe.crearProductoCompleto(producto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * Controlador para agregar categorías a un producto.
     * @param productoCat : Valores simples y necesarios del producto.
     * @return
     */
    @PatchMapping("/categories/update")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin')")
    public ResponseEntity<ProductoCompletoDTO> agregarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productoCat
    ){
        ProductoCompletoDTO producto = proSe.agregarCategorias(productoCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

    /**
     * Controlador para eliminar categorías de un producto.
     * Solo "Admin" y "Empresa" puede acceder a esta función.
     * @param productCat : Valores mínimos de un producto.
     * @return
     */
    @PatchMapping("/categories/delete")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin')")
    public ResponseEntity<ProductoCompletoDTO> eliminarCategorias(
            @Valid @RequestBody ProductoCategoriaDTO productCat
    ){
        ProductoCompletoDTO producto = proSe.eliminarCategorias(productCat);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }


    /**
     * Función que cambia el stock de un producto, ya sea agregando o quitando.
     * Solo "Admin" y "Empresa" puede acceder a esta función.
     * @param ID : ID del producto que se cambiara.
     * @param stockNew : Nuevo stock para el producto.
     * @return : Regresará el producto pero con su nuevo cambio.
     */
    @PatchMapping("/{ID}")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin')")
    public ResponseEntity<ProductoCompletoDTO> cambiarStock(
            @PathVariable @NotNull(message = "Ingrese un ID") UUID ID,
            @RequestParam(name = "stock")
            @PositiveOrZero(message = "Ingrese una cantidad positiva")
            @NotNull(message = "Ingrese una cantidad.") Integer stockNew
    ){
        ProductoCompletoDTO producto = proSe.cambiarStock(ID, stockNew);
        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }


    /**
     * Función que eliminara el producto, aunque verdaderamente cambiara el estado
     * del producto de activo -> desactivado.
     * Solo la empresa y los administradores pueden eliminar un producto.
     * @param ID : ID del producto.
     * @return : Regresará un mapa, con la acción realizada.
     */
    @PatchMapping("/delete/{ID}")
    @PreAuthorize("hasAnyRole('ROLE_Empresa', 'ROLE_Admin')")
    public ResponseEntity<Map<String, String>> eliminarProducto(
            @PathVariable @Validated UUID ID
            ){
        proSe.eliminarProducto(ID);
        return new ResponseEntity<>(Map.of("Acción:","Producto eliminado."), HttpStatus.NO_CONTENT);
    }
}
