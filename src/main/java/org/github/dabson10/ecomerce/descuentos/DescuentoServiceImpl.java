package org.github.dabson10.ecomerce.descuentos;

import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCompletoDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;

import java.util.UUID;

public interface DescuentoServiceImpl {
    public DescuentoCompletoDTO crearDescuento(DescuentoCreateDTO descuentoDTO);
    public DescuentoCompletoDTO desactivarDescuento(UUID ID);
}
