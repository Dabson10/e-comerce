package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCompletoDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoProyeccionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Productos paraProductos(ProductoCreateDTO productoCree);
    ProductoCompletoDTO paraProductoSimpleDTO(Productos productos);

    @Mapping(source = "idTienda", target = "id_tienda")
    @Mapping(source = "idProducto", target = "ID")
    @Mapping(source = "idDescuento", target = "descuentos.ID")
    @Mapping(source = "descuento", target = "descuentos.cantidad_descuento")
    @Mapping(source = "fechaInicio", target = "descuentos.fecha_inicio")
    @Mapping(source = "fechaFin", target = "descuentos.fecha_fin")
    @Mapping(source = "activo", target = "descuentos.activo")
    ProductoMostrarDTO paraProductoMostrarDTO(ProductoProyeccionDTO productoProyeccion);

    default OffsetDateTime mapInstantOffsetDateTime(Instant instant){
        if(instant == null){
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }


}
