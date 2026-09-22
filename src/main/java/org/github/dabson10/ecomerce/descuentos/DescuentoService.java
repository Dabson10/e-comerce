package org.github.dabson10.ecomerce.descuentos;

import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoSimpleDTO;
import org.github.dabson10.ecomerce.exception.DateException;
import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.productos.ProductoRepository;
import org.github.dabson10.ecomerce.productos.Productos;
import org.springframework.stereotype.Service;

import java.time.*;

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
    public DescuentoSimpleDTO crearDescuento(DescuentoCreateDTO descuentoDTO) {
        //Validamos que el producto exista.
        if(!proRe.existsById(descuentoDTO.getId_producto())){
            //Si no existe el producto entonces regresamos una exception
            throw new NotFoundEntityException("No se encontró producto con ese ID.");
        }
        //Validamos que la fecha ingresada que sea diferente a null sea futura y no una fecha pasada.
        OffsetDateTime ahora = OffsetDateTime.now();
        if(descuentoDTO.getFecha_fin() != null && descuentoDTO.getFecha_fin().isBefore(ahora)){
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


}
