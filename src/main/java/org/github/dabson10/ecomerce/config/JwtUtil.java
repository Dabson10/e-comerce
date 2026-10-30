package org.github.dabson10.ecomerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    //Norma en la que se rige el token
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //Tiempo de la existencia del token (6 Horas)
    private static final long expirationTime = 21600000;

    /**
     * Función que genera el token uniendolo con el subject o datos del token
     * @param subject
     * @return
     */
    public static String generateToken(String subject, Map<String, Object> claimCustom){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()//Builder para empezar con un constructor.
                .setClaims(claimCustom)
                .setSubject(subject)//Datos de quien emite el token.
                .setIssuedAt(now)//En qué momento se emitió el token
                .setExpiration(expiryDate)//momento en el que expira el token
                .signWith(key)//Firma del token, que incluye el header y el payload.
                .compact();//Se compacta para hacer una cadena de texto en 3 partes separadas por puntos.
    }


    /**
     * Más que revisar, esta función extrae o lee el contenido que se guardo en el token
     * @param token : Cadena de texto en base 64
     * @return
     */
    public static String getSubjectFromToken(String token){
        Claims claims = Jwts.parserBuilder()// Crea el constructor del parser.
                .setSigningKey(key)//Se indica que tipo de llave es y con que se verifica.
                .build()//Construye el parser ya configurado.
                .parseClaimsJws(token)//Verifica la firma, revisa que no haya expirado y devuelve el token ya parseado
                .getBody();//Saca el payload y regresa el subject
        return claims.getSubject();
    }


    /**
     * Valida que el token no cambie. Aparte valida que la firma del token sea valida.
     * @param token
     * @return
     */
    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder()//Crea el constructor del parser.
                    .setSigningKey(key)//Indica que tipo de llave y se verifica.
                    .build()//Se construye el parser ya configurado.
                    .parseClaimsJws(token);//Valida el token?
            return true;
        }catch(Exception e){
            return false;
        }
    }


    /**
     * Obtiene la fecha de expiración almacenada dentro del token para saber cuando caduca.
     * @param token : Cadena de texto en base 64 que contiene los datos ingresados en la primera función.
     * @return
     */
    public static Date getExpirationDate(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public static Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getClaimFromToken(String token, String claimName){
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimName, String.class);
    }

}
