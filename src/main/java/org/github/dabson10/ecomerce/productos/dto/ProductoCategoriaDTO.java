package org.github.dabson10.ecomerce.productos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class ProductoCategoriaDTO {
    @NotNull(message = "Ingrese un ID de producto.")
    private UUID idProducto;
    @NotNull(message = "Ingrese por lo menos una categoria.")
    private Set<UUID> idCategorias;
}
