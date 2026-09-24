package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCategoriaDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;

import java.util.UUID;

public interface ProductoServiceImpl {
    //Función para crear un producto.
    public ProductoSimpleDTO crearProductoSimple(ProductoCreateDTO productoCre);
    /**
     * Función para mostrar un producto con su descuento. Como tal esta será lo que se mostrara cuando
     * se busca un producto en específico.
     */
    public ProductoMostrarDTO mostrarProducto(UUID ID);
    //Cambiar stock en un producto.
    public ProductoSimpleDTO cambiarStock(UUID id, int stockNuevo);
    //Función para agregar una o mas categorías a un producto existente.
    public ProductoSimpleDTO agregarCategorias(ProductoCategoriaDTO productoCategoria);
    public ProductoSimpleDTO eliminarCategorias(ProductoCategoriaDTO productoCategoria);
    //Función para mostrar un producto.
    public void eliminarProducto(UUID ID);
}
