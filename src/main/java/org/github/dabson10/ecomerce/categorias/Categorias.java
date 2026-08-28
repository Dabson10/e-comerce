package org.github.dabson10.ecomerce.categorias;
import jakarta.persistence.*;
import org.github.dabson10.ecomerce.productos.Productos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 80)
    private String nombre;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
    //Relación N:N de productos a categorias intermedia tipo producto_categoria
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private List<Productos> productos;
}
