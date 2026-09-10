package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;

import java.util.UUID;

public interface ProductoServiceImpl {
    //Función para crear un producto.
    public ProductoSimpleDTO crearProductoSimple(ProductoCreateDTO productoCre);
    //Función para mostrar un producto.
    public void eliminarProducto(UUID ID);
}
