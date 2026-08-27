package org.github.dabson10.ecomerce.detalle_ordenes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Detalle_Ordenes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @Positive
    private Integer cantidad;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio_unitario;
    //Relación con producto y ordenes.
}
