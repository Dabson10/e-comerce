package org.github.dabson10.ecomerce.utility;

import org.github.dabson10.ecomerce.config.ConfigSecurity;
import org.springframework.stereotype.Component;

/**
 * Esta clase contendrá dos funciones clave para las contraseñas,
 * tanto para crear un hash como para comparar claves planas con claves hash
 */
@Component
public class ClaveUtility {
    private final ConfigSecurity coSe;
    public ClaveUtility(ConfigSecurity coSe){
        this.coSe = coSe;
    }

    /**
     * Esta función sirve para codificar una clave plana
     * a una clave con un hash más seguro.
     * @param clavePlana : String con la clave plana
     * @return : Regresará la clave con hash
     */
    public String encode(String clavePlana){
        return coSe.PasswordEncoder().encode(clavePlana);
    }

    /**
     * Esta función sirve para comparar una clave plana que proviene de la petición
     * con una clave Hash que proviene de una BD
     * @param clavePlana : Contraseña ingresada por usuario
     * @param claveHash : Contraseña obtenida de la base de datos.
     * @return : Si la clave hace match regresa true, si no un false.
     */
    public boolean comparar(String clavePlana, String claveHash){
        return coSe.PasswordEncoder().matches(clavePlana, claveHash);
    }
}
