package org.github.dabson10.ecomerce.tiendas;

import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaPresentacionDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;
import org.github.dabson10.ecomerce.usuarios.proyeccion.TiendaProductosProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TiendaMapper {
    Tiendas paraTiendas(TiendaCreateDTO tiendaDTO);

    @Mapping(source = "usuario.ID", target = "id_usuario")
    TiendaSimpleDTO paraTiendasSimpleDTO(Tiendas tiendas);

    /**
     * Este mapper sirve para obtener solo los datos proyectados de la tienda a un DTO,
     * no meterá los datos de los productos.
     */
    @Mapping(source = "idTienda", target = "id_tienda")
    @Mapping(source = "nombreTienda", target = "nombre_tienda")
    @Mapping(source = "descripcion", target = "descripcion")
    TiendaPresentacionDTO paraTiendaPresentacion(TiendaProductosProyeccion proyeccion);
}
