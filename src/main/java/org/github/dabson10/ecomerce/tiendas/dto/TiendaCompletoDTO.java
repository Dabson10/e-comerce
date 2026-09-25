package org.github.dabson10.ecomerce.tiendas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCompletoDTO {
    private UUID ID;
    private String nombreTienda;
    private String descripcion;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
//    private UUID id_usuario;
    private UsuarioSimpleDTO usuario;
    private List<ProductoSimpleDTO> productos;
    //Te falta agregar el List<OrdenesSimples>, ya que aún no inicias esa parte.
}
