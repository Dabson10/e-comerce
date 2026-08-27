package org.github.dabson10.ecomerce.tiendas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Tiendas {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Ingrese un nombre de una tienda.")
    private String nombre_tienda;
    @Column//Puede recibir null y el rango es con TEXT, no tiene un rango especifico.
    private String descripcion;
    @Column(nullable = false)
    @NotNull(message = "Ingrese un valor de estado.")
    private Boolean activo;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
}
