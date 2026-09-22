package org.github.dabson10.ecomerce.descuentos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DescuentoCreateDTO {
    @NotNull(message = "Ingrese una cantidad del descuento")
    @Positive(message = "Ingrese un valor positivo.")
    @Min(0) @Max(100)
    private short cantidad_descuento;
    @NotNull(message = "Ingrese un id de producto.")
    private UUID id_producto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_fin;
}
