package org.github.dabson10.ecomerce.usuarios;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioClavesDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCreateDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioCredencial;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private UsuarioService usuSe;
    public UsuarioController(UsuarioService usuSe){
        this.usuSe = usuSe;
    }

    /**
     * Este controlador sirve para crear un usuario, este controlador realiza sus funciones llamando al
     * service del usuario.
     * @param usuario : Recibe los datos más fundamentales del cliente, como:
     *               nombre, correo, clave y rol.
     * @return : Regresa un DTO con los datos formateados entre ellos: nombre, correo, rol y creado_en.
     */
    @PostMapping("/create")
    public ResponseEntity<UsuarioSimpleDTO> crearUsuario(
            @Valid @RequestBody UsuarioCreateDTO usuario
    ){
        UsuarioSimpleDTO usuDTO = usuSe.CrearUsuario(usuario);
        return new ResponseEntity<>(usuDTO, HttpStatus.CREATED);
    }


    /**
     * Esta función sirve como un login en donde podremos validar credenciales
     * @param credencial : Contiene correo y clave
     * @return : Regresa los datos del usuario.
     */
    @GetMapping("/reed")
    public ResponseEntity<UsuarioSimpleDTO> logUsuario(
            @RequestBody UsuarioCredencial credencial
            ){
        UsuarioSimpleDTO usuario = usuSe.logUsuario(credencial);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioSimpleDTO> actualizarDatosBasicos(
            @PathVariable UUID id,
            @RequestParam(name = "nombre") @NotBlank(message = "Ingrese un nombre nuevo.") String nombre
            ){
        UsuarioSimpleDTO usuario = usuSe.actualizarUsuario(id, nombre);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PatchMapping("/key")
    public ResponseEntity<UsuarioSimpleDTO> actualizarContraseña(
            @RequestParam(name = "email") String correo,
            @RequestBody UsuarioClavesDTO credenciales
    ){
        UsuarioSimpleDTO usuario = usuSe.actualizarContraseña(correo, credenciales);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> eliminarUsuario(
            @PathVariable UUID id
    ){
        usuSe.eliminarUsuario(id);
        return new ResponseEntity<>(Map.of("Tipo: ","Usuario eliminado."), HttpStatus.NO_CONTENT);
    }
}
