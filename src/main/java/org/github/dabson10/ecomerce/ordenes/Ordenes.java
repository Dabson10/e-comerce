package org.github.dabson10.ecomerce.ordenes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.enums.Estado_Orden;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Ordenes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    private Estado_Orden estado;
    @Column(nullable = false, precision = 12, scale = 2)//Rango para NUMERIC(12,2)
    @Min(0)
    @PositiveOrZero
    private BigDecimal total;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
}
