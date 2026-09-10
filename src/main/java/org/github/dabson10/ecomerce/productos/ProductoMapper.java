package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Productos paraProductos(ProductoCreateDTO productoCree);
    ProductoSimpleDTO paraProductoSimpleDTO(Productos productos);
}
