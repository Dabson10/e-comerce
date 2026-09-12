package org.github.dabson10.ecomerce.ExceptionGlobal;


import jakarta.validation.ConstraintViolationException;
import org.github.dabson10.ecomerce.exception.*;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionGlobal implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Esta función sirve para encubrir el cuerpo de la petición con datos extra, en este
     * caso, regresará una acción y el resultado.
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return : El mismo JSON solo que envuelto en parametros nuevos.
     */
    @Override
    public @Nullable Object beforeBodyWrite(
            @Nullable Object body, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        Map<String, Object> result = new HashMap<>();

        if(response instanceof ServletServerHttpResponse servletResponse){
            int status = servletResponse.getServletResponse().getStatus();

            if(status >= 400){
                //Si es mayor a un status code a 400 entonces regresamos un FALSE en donde
                //si recibe un status de error entonces
                result.put("success", false);
                result.put("errors", body);
                return result;
            }
        }
        //Si el cuerpo de la petición es un null entonces regresamos un TURE pero regresamos un null
        //en el cuerpo de petición.
        if(body == null){
            result.put("success", true);
            result.put("data", null);
            return result;
        }
        //Ahora si la petición es correcta y no regresa un vacío entonces regresará un
        //TRUE y los datos del cuerpo.
        result.put("success", true);
        result.put("data", body);
        return result;
    }

    /**
     * Manejo de error para la librería Validate en específico {@code @Valid}, este error captura faltas ya sea
     * en entidades vacías, nulas o email mal escrito.
     * @param errores : Este error abarca @NotNull, @NotBlank, @Email, etc. Captura y
     *                regresa un mensaje en donde no se cumplió el contrato.
     * @return :Regresa un mapa con 1 o más errores de entrada.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validVaciosValid(
            MethodArgumentNotValidException errores
    ){
        Map<String, String> mapa = new HashMap<>();

        errores.getBindingResult().getFieldErrors().forEach(err->
                mapa.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }

    /**
     * Esta exception capta errores que cubre la annotation {@code @Validated},
     * que se reciben por parámetro en el Controller, es similar a {@code @Valid} la unica diferencia
     * es esa que esta captura los errores que están escritos en variables escritas en parámetros.
     * @param errores : Este error abarca @NotNull, @NotBlank, @Email, etc. Captura y
     *                  regresa un mensaje en donde no se cumplió el contrato.
     * @return : Regresa un mapa con 1 o más errores de entrada.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> validVaciosValidated(
            ConstraintViolationException errores
    ){
        Map<String, String> mapa = new HashMap<>();

        errores.getConstraintViolations().forEach(err ->{
            String path = err.getPropertyPath().toString();
            String ubicacion = path.substring(path.lastIndexOf(".") + 1);
            mapa.put(ubicacion,err.getMessage());
        });
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }

    //Exception cuando intentan utilizar un correo existente.
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<Object> correoDuplicado(
            EmailDuplicateException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando buscan un usuario por correo, pero no existe este.
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Object> correoNoEncontrado(
            EmailNotFoundException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando se ingresa una contraseña incorrecta.
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Object> contraseñaIncorrecta(
            PasswordException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    //Exception cuando una entidad es duplicada o ya está guardada en BD
    @ExceptionHandler(EntityDuplicateException.class)
    public ResponseEntity<Object> entidadDuplicada(
            EntityDuplicateException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Object> entidadNoEncontrada(
            NotFoundEntityException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
    }
    //
    @ExceptionHandler(StockException.class)
    public ResponseEntity<Object> errorEnStock(
            StockException error
    ){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("Error tipo: ", error.getMessage());
        return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
    }
}
