package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.*;
import org.github.dabson10.ecomerce.usuarios.proyeccion.TiendaProductosProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

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


    @Mapping(source = "idProducto", target = "id_producto")
    @Mapping(source = "nombreProducto", target = "nombre_producto")
    @Mapping(source = "cantidadDescuento", target = "cantidad_descuento")
    @Mapping(source = "descuentoStatus", target = "descuento_status")
    @Mapping(source = "fechaInicio", target = "fecha_inicio")
    @Mapping(source = "fechaFin", target = "fecha_fin")
    ProductoPresentacionDTO paraProductoPresentacionDTO(TiendaProductosProyeccion productos);

    List<ProductoPresentacionDTO> paraProductoPresentacionDTO(List<TiendaProductosProyeccion> productos);

    default OffsetDateTime mapInstantOffsetDateTime(Instant instant){
        if(instant == null){
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }


}
