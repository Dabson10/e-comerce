package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCompletoDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoProyeccionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Productos paraProductos(ProductoCreateDTO productoCree);
    ProductoCompletoDTO paraProductoSimpleDTO(Productos productos);
    ProductoMostrarDTO paraProductoMostrarDTO(ProductoProyeccionDTO productoProyeccion);


}
