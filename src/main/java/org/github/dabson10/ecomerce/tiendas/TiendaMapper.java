package org.github.dabson10.ecomerce.tiendas;

import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TiendaMapper {
    Tiendas paraTiendas(TiendaCreateDTO tiendaDTO);

    @Mapping(source = "usuario.ID", target = "id_usuario")
    TiendaSimpleDTO paraTiendasSimpleDTO(Tiendas tiendas);
}
