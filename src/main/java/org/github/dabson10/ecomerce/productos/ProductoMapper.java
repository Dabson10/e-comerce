package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.*;
import org.github.dabson10.ecomerce.productos.proyeccion.ProductoProyeccion;
import org.github.dabson10.ecomerce.productos.utilities.Precios;
import org.github.dabson10.ecomerce.usuarios.proyeccion.TiendaProductosProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {Precios.class},
        imports = {org.github.dabson10.ecomerce.productos.utilities.Precios.class})
public interface ProductoMapper {

    Productos paraProductos(ProductoCreateDTO productoCree);
    @Mapping(source = "tienda.ID", target = "id_tienda")
    ProductoCompletoDTO paraProductoCompletoDTO(Productos productos);

    @Mapping(source = "idTienda", target = "id_tienda")
    @Mapping(source = "nombreTienda", target = "nombre_tienda")
    @Mapping(source = "idProducto", target = "ID")
    @Mapping(source = "idDescuento", target = "descuentos.ID")
    @Mapping(source = "descuento", target = "descuentos.cantidad_descuento")
    @Mapping(source = "fechaInicio", target = "descuentos.fecha_inicio")
    @Mapping(source = "fechaFin", target = "descuentos.fecha_fin")
    @Mapping(source = "activo", target = "descuentos.activo")
    @Mapping(target = "precio_descuento", expression = "java(Precios.calcularDescuento(productoProyeccion.getPrecio(), productoProyeccion.getDescuento()))")
    ProductoMostrarDTO paraProductoMostrarDTO(ProductoProyeccion productoProyeccion);

    List<ProductoMostrarDTO> paraListaProductoMostrarDTO(List<ProductoProyeccion> productoProyeccion);


    @Mapping(source = "idProducto", target = "id_producto")
    @Mapping(source = "nombreProducto", target = "nombre_producto")
    @Mapping(source = "cantidadDescuento", target = "cantidad_descuento")
    @Mapping(source = "descuentoStatus", target = "descuento_status")
    @Mapping(source = "fechaInicio", target = "fecha_inicio")
    @Mapping(source = "fechaFin", target = "fecha_fin")
    @Mapping(target = "precio_descuento", expression = "java(Precios.calcularDescuento(productos.getPrecio(), productos.getCantidadDescuento()))")
    ProductoPresentacionDTO paraProductoPresentacionDTO(TiendaProductosProyeccion productos);

    List<ProductoPresentacionDTO> paraProductoPresentacionDTO(List<TiendaProductosProyeccion> productos);


    default OffsetDateTime mapInstantOffsetDateTime(Instant instant){
        if(instant == null){
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }


}
