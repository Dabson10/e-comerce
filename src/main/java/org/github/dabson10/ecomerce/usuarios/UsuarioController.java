package org.github.dabson10.ecomerce.usuarios;

import jakarta.validation.Valid;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private UsuarioService usuSe;
    public UsuarioController(UsuarioService usuSe){
        this.usuSe = usuSe;
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioSimpleDTO> crearUsuario(
            @Valid @RequestBody UsuarioCreateDTO usuario
    ){
        usuSe.CrearUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
