package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.usuarios.dto.*;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioServiceImp {
    //Función para crear un usuario.
    public UsuarioSimpleDTO CrearUsuario(UsuarioCreateDTO usuario);
    public Token logUsuario(UsuarioCredencial credencial);
    public UsuarioSimpleDTO actualizarUsuario(UUID ID, String nombre);
    public UsuarioSimpleDTO actualizarContraseña(String correo, UsuarioClavesDTO claves);
    public void eliminarUsuario(UUID id);
}
