package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;

public interface UsuarioServiceImp {
    //Función para crear un usuario.
    public UsuarioSimpleDTO CrearUsuario(UsuarioCreateDTO usuario);
    //Función para verificar la existencia de un usuario.
    public Usuarios ExistenciaUsuario(String correo);
}
