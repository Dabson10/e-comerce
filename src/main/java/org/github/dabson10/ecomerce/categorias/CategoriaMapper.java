package org.github.dabson10.ecomerce.categorias;

import org.github.dabson10.ecomerce.categorias.dto.CategoriaSimpleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    public CategoriaSimpleDTO paraCategoriasSimpleDTO(Categorias categoria);
}
