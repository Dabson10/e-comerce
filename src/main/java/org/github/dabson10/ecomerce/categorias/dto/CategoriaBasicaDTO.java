package org.github.dabson10.ecomerce.categorias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoriaBasicaDTO {
    private String nombre;
    private UUID ID;
}
