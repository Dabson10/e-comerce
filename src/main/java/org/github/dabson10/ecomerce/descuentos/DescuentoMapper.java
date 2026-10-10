package org.github.dabson10.ecomerce.descuentos;

import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCompletoDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface DescuentoMapper {
    public Descuentos paraDescuentos(DescuentoCreateDTO descuentoDTO);

    default OffsetDateTime mapDescuentoOffsetDateTime(LocalDate fecha_fin){
        if(fecha_fin == null){
            return null;
        }
        return fecha_fin
                .atTime(LocalTime.MAX)
                .atZone(ZoneId.of("America/Mexico_City"))
                .toOffsetDateTime();
    }
    //Convertir de un descuento(entidad) a un DescuentoCompletoDTO
    public DescuentoCompletoDTO paraDescuentoSimpleDTO(Descuentos descuentos);
}
