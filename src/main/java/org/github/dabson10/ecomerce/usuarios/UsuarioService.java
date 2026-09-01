package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.exception.EmailDuplicateException;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.github.dabson10.ecomerce.utility.ClaveUtility;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public UsuarioSimpleDTO mostrarUsuario(String correo) {
        Optional<Usuarios> usuario = usuRe.findByCorreo(correo);
        if(!usuario.isPresent()){
            //Si el valor es vacio entonces mostramos un mensaje.
            System.out.println("Usuario no encontrado.");
            return null;
        }
        System.out.println("Usuario encontrado");
        return null;
    }


}
