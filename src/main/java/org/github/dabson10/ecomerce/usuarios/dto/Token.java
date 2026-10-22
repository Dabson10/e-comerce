package org.github.dabson10.ecomerce.usuarios.dto;


import lombok.Getter;
import java.time.Instant;

/**
 * Clase que mostrara datos relevantes del token generado, como
 * el valor de este, su tipo y la expiración.
 */
@Getter
public class Token {
    private String loginRespuesta;
    private final String tipo;
    private Instant expiracion;

    public Token(){
        this.tipo = "Bearer";
    }
    public Token(String loginRespuesta, Instant expiracion){
        this.loginRespuesta = loginRespuesta;
        this.tipo = "Bearer";
        this.expiracion = expiracion;
    }

}
