package org.github.dabson10.ecomerce.tiendas;

import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaPresentacionDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;
import org.github.dabson10.ecomerce.usuarios.proyeccion.TiendaProductosProyeccion;

import java.util.List;
import java.util.UUID;

public interface TiendaServiceImpl {

     TiendaSimpleDTO crearTienda(TiendaCreateDTO tienda);
     TiendaSimpleDTO mostrarDatosTienda(UUID ID);
     //Mostrar los productos de la tienda.
     TiendaPresentacionDTO mostrarProductosTienda(UUID ID);
     void eliminarEmpresa(UUID ID);
}
