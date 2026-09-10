package org.github.dabson10.ecomerce.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCreateDTO {
    private UUID id;
    @NotBlank(message = "Ingrese un nombre.")
    private String nombre;
    private String descripcion;
    @NotNull(message = "Ingrese un precio del producto.")
    @PositiveOrZero(message = "Ingrese una precio positiva.")
    private BigDecimal precio;
    @NotNull(message = "Ingrese una cantidad del producto.")
    @PositiveOrZero(message = "Ingrese una cantidad positiva.")
    private Integer stock;
    @NotNull(message = "Ingrese un ID de tienda.")
    private UUID id_tienda;
}
