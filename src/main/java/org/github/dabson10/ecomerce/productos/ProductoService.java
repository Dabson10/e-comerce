package org.github.dabson10.ecomerce.productos;

import lombok.extern.slf4j.Slf4j;
import org.github.dabson10.ecomerce.categorias.Categorias;
import org.github.dabson10.ecomerce.categorias.CategoriasRepository;
import org.github.dabson10.ecomerce.exception.EmptyCollectionException;
import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.exception.StockException;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
import org.github.dabson10.ecomerce.tiendas.TiendaRepository;
import org.github.dabson10.ecomerce.tiendas.Tiendas;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class ProductoService implements ProductoServiceImpl{

    private final ProductoMapper proMa;
    private final ProductoRepository proRe;
    private final TiendaRepository tiRe;
    private final CategoriasRepository caRe;

    public ProductoService(ProductoRepository proRe, TiendaRepository tiRe,
                           ProductoMapper proMa, CategoriasRepository caRe){
        this.proRe = proRe; this.tiRe = tiRe;
        this.proMa = proMa; this.caRe = caRe;
    }

    /**
     * Esta función servirá para crear un priducto y a su vez asignarle una o más categorías.
     * Por lo que en esta función se creara tanto el producto como la relación entre producto<->categoria
     * @param productoCre :Valores del producto.
     * @return : Regresará un DTO simple para mostrar los datos ya insertados del producto.
     */
    @Transactional
    @Override
    public ProductoSimpleDTO crearProductoSimple(ProductoCreateDTO productoCre) {
        //Validamos que exista la tienda.
        if(!tiRe.existsTiendasByID(productoCre.getId_tienda())){
            //Si la tienda no existe entonces regresamos una exception.
            throw new NotFoundEntityException("No se encontró la tienda con ese ID.");
        }
        //Empezamos con validaciones con respecto a si la lista de categorias esta vacía.
        if(productoCre.getId_categorias().isEmpty()){
            //Si está vacía entonces regresamos una exception.
            throw new NotFoundEntityException("No se ingresaron categorías al producto.");
        }
        //Ahora tendremos que hacer otra validación, pero esta será diferente. Ya que debemos validar
        //que los ID's existan en categorias y filtrar su la lista dejando solo las existentes.
        List<Categorias> categorias = listaFiltrada(productoCre.getId_categorias());

        //Ahora que eliminamos las categorias que no corresponden con su ID toca otra validación,
        //por si la lista quedo vacía, esto porque no se encontró nada en BD.
        if(categorias.isEmpty()){
            //Si la lista con datos filtrados esta vacía regresamos exception.
            throw new EmptyCollectionException("No se encontró la existencia de las categorías.");
        }

        //Ahora teniendo los valores de la tienda toca formatear de ProductoCreateDTO a Producto
        Productos productos = proMa.paraProductos(productoCre);
        //Guardamos una referencia de Tiendas y categorias solo con los ID's.
        productos.setTienda(tiRe.getReferenceById(productoCre.getId_tienda()));
        productos.setCategorias(categorias);
        //Realizamos el guardado en base de datos y guardamos los valores de inserción.
        productos = proRe.save(productos);
        return proMa.paraProductoSimpleDTO(productos);
    }

    /**
     *Esta función servirá para filtrar la colección de Set y buscar categorias existentes en BD,
     * esto es para no agregar valores inexistentes y poder cerrar la puerta si es que los valores no existe.
     * @param id_categorias : Un Set con los ID's de las categorías
     * @return : Regresará una lista con un objeto y solo su referencia del ID.
     */
    public List<Categorias> listaFiltrada(Set<UUID> id_categorias){
        List<Categorias> categorias = new ArrayList<>();
        //Ahora lo que haremos es recorrer la lista y buscar en BD si encuentra
        //el valor entonces regresamos la referencia.
        id_categorias.forEach(ID ->{
            if(caRe.existsByID(ID)){
                //Si existe entonces guardamos la referencia del ID en un objeto
                categorias.add(caRe.getReferenceById(ID));
            }
        });
        return categorias;
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
