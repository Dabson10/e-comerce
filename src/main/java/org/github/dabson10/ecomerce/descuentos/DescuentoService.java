package org.github.dabson10.ecomerce.descuentos;

import lombok.extern.slf4j.Slf4j;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCompletoDTO;
import org.github.dabson10.ecomerce.exception.DateException;
import org.github.dabson10.ecomerce.exception.EntityDuplicateException;
import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.exception.ValueException;
import org.github.dabson10.ecomerce.productos.ProductoRepository;
import org.github.dabson10.ecomerce.productos.Productos;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.UUID;

@Slf4j
@Service
public class DescuentoService implements DescuentoServiceImpl{

    private final ProductoRepository proRe;
    private final DescuentoRepository deRe;
    private final DescuentoMapper deMa;

    public DescuentoService(ProductoRepository proRe, DescuentoRepository deRe,
                            DescuentoMapper deMa){
        this.proRe = proRe; this.deRe = deRe;
        this.deMa = deMa;
    }

    /**
     * Función para crear y asignar un descuento a un producto existente.
     * @param descuentoDTO : Datos básicos del descuento y el ID del producto.
     * @return : Regresará el descuento con un producto simple.
     */
    @Override
    public DescuentoCompletoDTO crearDescuento(DescuentoCreateDTO descuentoDTO) {
        //Validamos que el producto exista.
        if(!proRe.existsById(descuentoDTO.getId_producto())){
            //Si no existe el producto entonces regresamos una exception
            throw new NotFoundEntityException("No se encontró producto con ese ID.");
        }
        //Validamos que el producto no tenga un descuento activo.
        if(proRe.existDescuentoProducto(descuentoDTO.getId_producto())){
            throw new EntityDuplicateException("El producto tiene un descuento activo. El descuento activo debe de estar inactivo.");
        }
        //Validamos que la fecha ingresada que sea diferente a null sea futura y no una fecha pasada.
        OffsetDateTime ahora = OffsetDateTime.now();
        if(descuentoDTO.getFecha_fin() != null && descuentoDTO.getFecha_fin().isBefore(ahora.toLocalDate())){
            throw new DateException("La fecha del fin del descuento es incorrecta. Ingrese una fecha futura. ");
        }

        //Ahora obtenemos un objeto con referencia de ID del producto
        Productos producto = proRe.getReferenceById(descuentoDTO.getId_producto());
        //Ahora que ya tenemos el producto tenemos que agregarlo en el descuento
        Descuentos descuento = deMa.paraDescuentos(descuentoDTO);
        //Ahora metemos el producto.
        descuento.setProducto(producto);
        //Guardamos
        descuento = deRe.save(descuento);
        return deMa.paraDescuentoSimpleDTO(descuento);
    }

    @Override
    public DescuentoCompletoDTO desactivarDescuento(UUID ID) {
        //Buscamos el descuento mediante el ID.
        Descuentos descuento = deRe.findById(ID)
                .orElseThrow(() -> new NotFoundEntityException("No se encontró descuento con ese ID"));
        if(descuento.getActivo() == false){
            //Si es false entonces no procedimos y mandamos una exception.
            throw new ValueException("El descuento ya esta desactivado.");
        }
        //Ahora como se encontró el descuento toca eliminarlo cambiando el estado de activo de este.
        descuento.setActivo(false);
        //Guardamos o actualizamos el descuento
        deRe.save(descuento);
        //Mandamos un DTO resumido.
        return deMa.paraDescuentoSimpleDTO(descuento);
    }


}
