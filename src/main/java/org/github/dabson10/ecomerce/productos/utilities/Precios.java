package org.github.dabson10.ecomerce.productos.utilities;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Precios {

    public static BigDecimal calcularDescuento(BigDecimal precio, Short cantidadDescuento){
        if(cantidadDescuento == null){
            //Si cantidad descuento es null regresamos el precio, porque no hay descuento.
            return precio;
        }
        //Si hay una cantidad de descuento toca calcularlo.
        BigDecimal descuento = (precio.multiply(BigDecimal.valueOf(cantidadDescuento))).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        return precio.subtract(descuento);
    }

}
