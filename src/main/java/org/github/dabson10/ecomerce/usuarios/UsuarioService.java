package org.github.dabson10.ecomerce.usuarios;

import org.github.dabson10.ecomerce.exception.EmailDuplicateException;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.github.dabson10.ecomerce.utility.ClaveUtility;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioServiceImp{

    private UsuarioRepository usuRe;
    private final ClaveUtility claUt;
    public UsuarioService(UsuarioRepository usuRe, ClaveUtility claUt){
        this.usuRe = usuRe;
        this.claUt = claUt;
    }


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
        Usuarios usu =
    }

    /**
     * Esta función buscará por correo electrónico coincidencias en la base de datos,
     * y regresar un Boolean
     * @param correo
     * @return
     */
    @Override
    public Usuarios ExistenciaUsuario(String correo) {
        return null;
    }
}
