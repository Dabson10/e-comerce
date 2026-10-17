package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCategoriaDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCompletoDTO;

import java.util.List;
import java.util.UUID;

public interface
ProductoServiceImpl {
    //Función para crear un producto.
    public ProductoCompletoDTO crearProductoCompleto(ProductoCreateDTO productoCre);
    /**
     * Función para mostrar un producto con su descuento. Como tal esta será lo que se mostrara cuando
     * se busca un producto en específico.
     */
    public ProductoMostrarDTO mostrarProducto(UUID ID);

    /**
    * Esta función mostrará productos filtrando por una categoria, por lo que apareceran
     * solo los productos que coincidan con la categoria.*/
    public List<ProductoMostrarDTO> productoCategorias(String categoria);
    //Cambiar stock en un producto.
    public ProductoCompletoDTO cambiarStock(UUID id, int stockNuevo);
    //Función para agregar una o mas categorías a un producto existente.
    public ProductoCompletoDTO agregarCategorias(ProductoCategoriaDTO productoCategoria);
    public ProductoCompletoDTO eliminarCategorias(ProductoCategoriaDTO productoCategoria);
    //Función para mostrar un producto.
    public void eliminarProducto(UUID ID);
}
