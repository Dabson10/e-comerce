package org.github.dabson10.ecomerce.descuentos;

import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoSimpleDTO;

import java.util.UUID;

public interface DescuentoServiceImpl {
    public DescuentoSimpleDTO crearDescuento(DescuentoCreateDTO descuentoDTO);
    public DescuentoSimpleDTO desactivarDescuento(UUID ID);
}
