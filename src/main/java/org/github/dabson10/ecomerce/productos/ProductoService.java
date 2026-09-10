package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.exception.StockException;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
import org.github.dabson10.ecomerce.tiendas.TiendaRepository;
import org.github.dabson10.ecomerce.tiendas.Tiendas;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductoService implements ProductoServiceImpl{

    private final ProductoMapper proMa;
    private final ProductoRepository proRe;
    private final TiendaRepository tiRe;

    public ProductoService(ProductoRepository proRe, TiendaRepository tiRe,
                           ProductoMapper proMa){
        this.proRe = proRe; this.tiRe = tiRe;
        this.proMa = proMa;
    }

    /**
     * Esta función sirve para crear un producto y asociarlo a una tienda, mediante
     * id_tienda.
     * @param productoCre :Valores del producto.
     * @return : Regresará un DTO simple para mostrar los datos ya insertados del producto.
     */
    @Override
    public ProductoSimpleDTO crearProductoSimple(ProductoCreateDTO productoCre) {
        //Validamos que exista la tienda.
        if(!tiRe.existsTiendasByID(productoCre.getId_tienda())){
            //Si la tienda no existe entonces regresamos una exception.
            throw new NotFoundEntityException("No se encontró la tienda con ese ID.");
        }

        //Ahora teniendo los valores de la tienda toca formatear de ProductoCreateDTO a Producto
        Productos productos = proMa.paraProductos(productoCre);
        //Guardamos una referencia de Tiendas solo con el ID de la tienda.
        productos.setTienda(tiRe.getReferenceById(productoCre.getId_tienda()));
        //Realizamos el guardado en base de datos y guardamos los valores de inserción.
        productos = proRe.save(productos);
        return proMa.paraProductoSimpleDTO(productos);
    }

    /**
     * Esta funcionalidad servirá para eliminar un producto, solo que
     * lo podremos eliminar siempre y cuando este no tenga productos en
     * stock
     * @param ID : ID del producto
     * @return : Regresara nada
     */
    @Override
    public void eliminarProducto(UUID ID) {
        //Buscaremos el producto.
        Productos producto = proRe.findById(ID)
                .orElseThrow(() -> new NotFoundEntityException("No se encontró el producto, ingresa uno correcto."));
        //Como ya tenemos la validación de la existencia del producto toca saber si su cantidad es mayor
        if(producto.getStock() > 0){
            //Si es mayor a cero entonces regresamos una exception.
            throw new StockException("No puede eliminar el producto aun hay en existencia.");
        }
        //Si no regresa exception actualizamos el estado del producto.
        producto.setActivo(false);
        proRe.save(producto);
    }
}
