package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    //mapper para UsuarioCreate a Usuario.
    Usuarios paraUsuarios(UsuarioCreateDTO usuario);

    //Mapper para Usuario a UsuarioSimpleDTO.
    UsuarioSimpleDTO paraUsuarioSimpleDTO(Usuarios usuario);
}
