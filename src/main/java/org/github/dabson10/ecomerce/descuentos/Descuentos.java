package org.github.dabson10.ecomerce.descuentos;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Descuentos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @PositiveOrZero
    private Integer cantidad_descuento;
    @Column(nullable = false)
    private OffsetDateTime fecha_inicio;
    @Column(nullable = false)
    private OffsetDateTime fecha_fin;
    //Relación con productos.
}
