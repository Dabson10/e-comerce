package org.github.dabson10.ecomerce.productos;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.categorias.Categorias;
import org.github.dabson10.ecomerce.descuentos.Descuentos;
import org.github.dabson10.ecomerce.detalle_ordenes.DetalleOrdenes;
import org.github.dabson10.ecomerce.tiendas.Tiendas;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda")
    private Tiendas tienda;
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Descuentos> descuentos;
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<DetalleOrdenes> detalles;
    //Relación N:N de productos a categorias intermedia tipo producto_categoria
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producto_categoria",
            joinColumns = @JoinColumn(name = "id_producto", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_categoria", nullable = false))
    private List<Categorias> categorias;
}
