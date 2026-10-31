package org.github.dabson10.ecomerce.usuarios;

import jakarta.persistence.EntityNotFoundException;
import org.github.dabson10.ecomerce.config.JwtUtil;
import org.github.dabson10.ecomerce.exception.*;
import org.github.dabson10.ecomerce.usuarios.dto.*;
import org.github.dabson10.ecomerce.utility.ClaveUtility;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
    public Token CrearUsuario(UsuarioCreateDTO usuario) {
        //Teniendo los datos del usuario tenemos ver si el usuario o en este caso el correo.
        if(usuRe.existsByCorreo(usuario.getCorreo().trim().toLowerCase())){
            //Si el correo existe entonces regresamos una exception
            throw new EmailDuplicateException("Correo existente. Ingrese uno diferente.");
        }
        //Teniendo la validación toca convertir la contraseña en Hash
        usuario.setClave(claUt.encode(usuario.getClave()));
        //Ahora pasamos de usuarioCreate a Usuario
        Usuarios usu = usuMa.paraUsuarios(usuario);
        //Creamos el usuario.
        usu = usuRe.save(usu);
        //Se tiene que obtener el rol del usuario y asignarlo en las claims.
        Map<String, Object> claimCustom = new HashMap<>();
        claimCustom.put("rol",("ROLE_" + usuario.getRol()));
        //Con el claim generamos el token
        String token = JwtUtil.generateToken(usuario.getCorreo(), claimCustom);
        //Regresamos el token del usuario.
        return new Token(token, JwtUtil.getExpirationDate(token).toInstant());
//        return usuMa.paraUsuarioSimpleDTO(usu);
    }

    /**
     * Esta función es para realizar el login, el cual al validar las credenciales del usuario,
     * regresará los datos un DTO de usuario con datos simples.
     * @param credencial : Tendrá correo y clave.
     * @return : Regresará los datos del usuario.
     */
    @Override
    public Token logUsuario(UsuarioCredencial credencial) {
        Optional<Usuarios> usuario = usuRe.findByCorreo(credencial.getCorreo().trim().toLowerCase());
        if(usuario.isEmpty()){
            //Si el valor es vacío entonces mostramos un mensaje.
            throw new CredencialesInvalidasException("Credenciales invalidas.");
        }
        //Ahora se realiza una validación de la contraseña del usuario.
        if(!claUt.comparar(credencial.getClave(), usuario.get().getClave())){
            //Si la clave es diferente, regresamos una exception.
            throw new CredencialesInvalidasException("Credenciales invalidas.");
        }
        //Debemos pasar un rol del usuario por lo que debemos mandar un Claim personalizado usando Map
        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("rol", ("ROLE_" + usuario.get().getRol()));
        //Generamos el token cuando el usuario se valide por completo.
        String tokenGen = JwtUtil.generateToken(usuario.get().getCorreo(), customClaims);
        //Como la contraseña es correcta entonces regresamos los datos del usuario.
        return new Token(tokenGen, JwtUtil.getExpirationDate(tokenGen).toInstant());
    }

    /**
     * Función para obtener el usuario y sus datos, mediante el valor de
     * la cabecera.
     * @param headerAuth : Cabecera de la petición.
     * @return : Regresará los datos del usuario.
     */
    @Override
    public UsuarioSimpleDTO datosUsuario(String headerAuth) {
        //Obtenemos el token de la cabecera de la petición.
        String token = headerAuth.replace("Bearer ", "");
        //Obtenemos el subject que sería el correo
        String correo = JwtUtil.getSubjectFromToken(token);
        //Buscamos en la base de datos el correo
        Optional<Usuarios> usuario = usuRe.findByCorreo(correo);
        if(usuario.isEmpty()){
            //Si el valor es vacío significa que no se encontró el usuario.
            throw new NotFoundEntityException("No se encontró usuario con ese correo.");
        }
        //Ahora que ya sabemos que existe el usuario, regresamos el usuario mapeado.
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
            throw new PasswordException("Para cambiar la contraseña, ingrese su contraseña original.");
        }
        if(claUt.comparar(claves.getClaveNueva(), usuario.getClave())){
            //Si son iguales entonces regresamos una exception
            throw new PasswordException("Ingrese una contraseña diferente a la anterior.");
        }
        //Después de la validación guardamos en el objeto la nueva clave con hash
        usuario.setClave(claUt.encode(claves.getClaveNueva()));
        //Ahora actualizamos
        usuario = usuRe.save(usuario);
        return usuMa.paraUsuarioSimpleDTO(usuario);
    }

    @Override
    public void eliminarUsuario(UUID id) {
        //Validamos que exista el usuario mediante su ID
        Usuarios usuario = usuRe.findById(id).orElseThrow(
                () -> new NotFoundEntityException("No se encontró el usuario."));
        //Ahora realizamos la eliminación del usuario, mediante un SoftDelete, por lo que ahora.
        if(!usuario.getActivo()){
            //Si el estado del usuario es false entonces regresamos diciendo que no se puede eliminar un usuario que ya está eliminado.
            throw new DeleteException("No se puede eliminar un usuario que ya esta eliminado.");
        }
        //Cambiamos el estado del usuario de true -> false para que el usuario se elimine lógicamente.
        usuario.setActivo(!usuario.getActivo());
        //Actualizamos los datos.
        usuRe.save(usuario);
    }


}
