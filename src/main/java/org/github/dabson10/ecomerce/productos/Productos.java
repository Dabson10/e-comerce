package org.github.dabson10.ecomerce.productos;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 80)
    @NotBlank(message = "Ingrese un nombre del producto")
    private String nombre;
    @Column
    private String descripcion;
    @Column(nullable = false, precision = 12, scale = 2)//Para NUMERIC(12,2)
    @Min(0)
    @PositiveOrZero//Se podría decir un >=0
    private BigDecimal precio;
    @Column(nullable = false)
    @PositiveOrZero
    private Integer stock;
    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
    //Relación con tienda.
}
