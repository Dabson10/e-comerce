package org.github.dabson10.ecomerce.ExceptionGlobal;


import org.github.dabson10.ecomerce.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionGlobal {

    /**
     * Manejo de error para la librería Validate, este error captura faltas ya sea
     * en entidades vacías, nulas o email mal escrito.
     * @param errores : Este error abarca @NotNull, @NotBlank, @Email, etc. Captura y
     *                regresa un mensaje en donde no se cumplió el contrato.
     * @return :Regresa un mapa con 1 o más errores de entrada.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validVacios(
            MethodArgumentNotValidException errores
    ){
        Map<String, String> mapa = new HashMap<>();

        errores.getBindingResult().getFieldErrors().forEach(err->
                mapa.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }

    //Exception cuando intentan utilizar un correo existente.
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<Map<String, String>> correoDuplicado(
            EmailDuplicateException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando buscan un usuario por correo, pero no existe este.
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> correoNoEncontrado(
            EmailNotFoundException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando se ingresa una contraseña incorrecta.
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Map<String, String>> contraseñaIncorrecta(
            PasswordException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando una entidad es duplicada o ya está guardada en BD
    @ExceptionHandler(EntityDuplicateException.class)
    public ResponseEntity<Map<String, String>> entidadDuplicada(
            EntityDuplicateException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Map<String, String>> entidadNoEncontrada(
            NotFoundEntityException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
    }
    //
    @ExceptionHandler(StockException.class)
    public ResponseEntity<Map<String, String>> errorEnStock(
            StockException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
    }
}
