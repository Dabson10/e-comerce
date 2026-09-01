package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;

import java.util.Optional;

public interface UsuarioServiceImp {
    //Función para crear un usuario.
    public UsuarioSimpleDTO CrearUsuario(UsuarioCreateDTO usuario);
    public UsuarioSimpleDTO mostrarUsuario(String correo);

}
