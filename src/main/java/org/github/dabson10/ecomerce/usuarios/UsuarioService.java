package org.github.dabson10.ecomerce.usuarios;

import jakarta.persistence.EntityNotFoundException;
import org.github.dabson10.ecomerce.exception.EmailDuplicateException;
import org.github.dabson10.ecomerce.exception.EmailNotFoundException;
import org.github.dabson10.ecomerce.exception.IncorrectPasswordException;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioClavesDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCredencial;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.github.dabson10.ecomerce.utility.ClaveUtility;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements UsuarioServiceImp{

    private final UsuarioRepository usuRe;
    private final ClaveUtility claUt;
    private final UsuarioMapper usuMa;
    public UsuarioService(UsuarioRepository usuRe, ClaveUtility claUt,
                          UsuarioMapper usuMa){
        this.usuRe = usuRe; this.claUt = claUt;
        this.usuMa = usuMa;
    }


    /**
     * Esta función sirve para crear usuarios, mediante los datos recibidos de
     * UsuarioCreateDTO
     * @param usuario : Objeto que contendrá los datos fundamentales del usuario.
     * @return  Regresará el usuario con datos simples.
     */
    @Override
    public UsuarioSimpleDTO CrearUsuario(UsuarioCreateDTO usuario) {
        //Teniendo los datos del usuario tenemos ver si el usuario o en este caso el correo.
        if(usuRe.existsByCorreo(usuario.getCorreo())){
            //Si el correo existe entonces regresamos una exception
            throw new EmailDuplicateException("Correo existente. Ingrese uno diferente.");
        }
        //Teniendo la validación toca convertir la contraseña en Hash
        usuario.setClave(claUt.encode(usuario.getClave()));
        //Ahora pasamos de usuarioCreate a Usuario
        Usuarios usu = usuMa.paraUsuarios(usuario);
        System.out.println(usu.getCreado_en());
        System.out.println(usu.getActualizado_en());
        usu = usuRe.save(usu);
        //En esta parte se guardan los datos del usuario
        return usuMa.paraUsuarioSimpleDTO(usu);
    }

    /**
     * Esta función es para realizar el login, el cual al validar las credenciales del usuario,
     * regresará los datos un DTO de usuario con datos simples.
     * @param credencial : Tendrá correo y clave.
     * @return : Regresará los datos del usuario.
     */
    @Override
    public UsuarioSimpleDTO logUsuario(UsuarioCredencial credencial) {
        Optional<Usuarios> usuario = usuRe.findByCorreo(credencial.getCorreo());
        if(usuario.isEmpty()){
            //Si el valor es vacío entonces mostramos un mensaje.
            throw new EmailNotFoundException("No se encontró usuario con ese correo electronico.");
        }
        //Ahora se realiza una validación de la contraseña del usuario.
        if(!claUt.comparar(credencial.getClave(), usuario.get().getClave())){
            //Si la clave es diferente, regresamos una exception.
            throw new IncorrectPasswordException("Contraseña incorrecta.");
        }
        //Como la contraseña es correcta entonces regresamos los datos del usuario.
        return usuMa.paraUsuarioSimpleDTO(usuario.get());
    }

    @Override
    public UsuarioSimpleDTO actualizarUsuario(UUID ID, String nombre) {
        Usuarios usuario = usuRe.findById(ID).orElseThrow(() ->
                new EntityNotFoundException("Usuario no encontrado.")
        );
        //Ahora teniendo los datos del usuario toca meter el nombre nuevo del usuario.
        usuario.setNombre(nombre);
        usuario = usuRe.save(usuario);
        return usuMa.paraUsuarioSimpleDTO(usuario);
    }

    @Override
    public UsuarioSimpleDTO actualizarContraseña(String correo, UsuarioClavesDTO claves) {
        Optional<Usuarios> usuarioOpt = usuRe.findByCorreo(correo);
        if(usuarioOpt.isEmpty()){
            //Si no se encuentra al usuario entonces regresa un error.
            throw new EmailNotFoundException("No se encontró correo electrónico. Ingrese uno correcto.");
        }
        Usuarios usuario = usuarioOpt.get();
        //Ahora se tiene que comparar la contraseña antigua con la de BD, para asi cambiar la contraseña.
        if(!claUt.comparar(claves.getClaveAntigua(), usuario.getClave())){
            throw new IncorrectPasswordException("Para cambiar la contraseña, ingrese su contraseña original.");
        }
        //Después de la validación guardamos en el objeto la nueva clave con hash
        usuario.setClave(claUt.encode(claves.getClaveNueva()));
        //Ahora actualizamos
//        usuario = usuRe.save(usuario);
        return usuMa.paraUsuarioSimpleDTO(usuario);
    }

    @Override
    public void eliminarUsuario(UUID id) {
        //Validamos que exista el usuario mediante su ID
        Usuarios usuario = usuRe.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No se encontró el usuario."));
        //Ahora realizamos la eliminación del usuario, mediante un SoftDelete, por lo que ahora.
        if(!usuario.getActivo()){
            //Si el estado del usuario es false entonces regresamos diciendo que no se puede eliminar un usuario que ya esta eliminado.
        }
        //Cambiamos el estado del usuario de true -> false para que el usuario se elimine lógicamente.
        usuario.setActivo(!usuario.getActivo());
        //Actualizamos los datos.
        usuRe.save(usuario);
    }


}
