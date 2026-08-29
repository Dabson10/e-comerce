package org.github.dabson10.ecomerce.ExceptionGlobal;

import org.github.dabson10.ecomerce.exception.EmailDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionGlobal {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validVacios(
            MethodArgumentNotValidException errores
    ){
        Map<String, String> mapa = new HashMap<>();

        errores.getBindingResult().getFieldErrors().forEach(err->
                mapa.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<Map<String, String>> correoDuplicado(
            EmailDuplicateException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
}
