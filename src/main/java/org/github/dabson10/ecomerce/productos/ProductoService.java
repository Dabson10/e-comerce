package org.github.dabson10.ecomerce.productos;

import lombok.extern.slf4j.Slf4j;
import org.github.dabson10.ecomerce.categorias.Categorias;
import org.github.dabson10.ecomerce.categorias.CategoriasRepository;
import org.github.dabson10.ecomerce.exception.EmptyCollectionException;
import org.github.dabson10.ecomerce.exception.EntityException;
import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.exception.StockException;
import org.github.dabson10.ecomerce.productos.dto.ProductoCategoriaDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoCreateDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;
import org.github.dabson10.ecomerce.tiendas.TiendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
     * Esta función servirá para crear un production y a su vez asignarle una o más categorías.
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
        //Empezamos con validaciones con respecto a si la lista de categorias está vacía.
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
     * Esta funcionalidad sirve para cambiar el stock de un producto.
     * @param id :Id del producto
     * @param stockNuevo : Nueva cantidad a ingresar, obviamente positiva.
     * @return : Regresará el producto formateado.
     */
    @Override
    public ProductoSimpleDTO cambiarStock(UUID id, int stockNuevo) {
        //Buscamos el producto y validamos su existencia.
        Productos producto = proRe.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("No se encontró producto con ese ID."));
        //Validamos que la cantidad que se ingresa sea diferente al que ya tiene.
        if(producto.getStock() == stockNuevo){
            //Si es igual entonces regresamos una exception y no actualizamos.
            throw new StockException("Ingrese una cantidad diferente.");
        }
        //Ahora realizamos el cambio del stock por el nuevo.
        producto.setStock(stockNuevo);
        producto = proRe.save(producto);
        return proMa.paraProductoSimpleDTO(producto);
    }

    /**
     * Esta función servirá para agregar mas categorias asociadas a un producto existente.
     * @param productoCategoria :Contendrá el ID del producto y los ID's de las categorías.
     * @return Regresará el mismo producto solo que con datos formateados.
     */
    @Override
    public ProductoSimpleDTO agregarCategorias(ProductoCategoriaDTO productoCategoria) {
        //Primero realizamos una validación para saber si el producto existe.
        Productos producto = proRe.findById(productoCategoria.getIdProducto())
                .orElseThrow(() -> new NotFoundEntityException("No se encontró el producto."));
        //Ahora realizaremos las validaciones de las categorias.
        if(productoCategoria.getIdCategorias().isEmpty()){
            //Si está vacía entonces regresamos una exception
            throw new EmptyCollectionException("Si quiere nuevas categorías, favor de agregarlas.");
        }
        List<Categorias> categorias = listaFiltrada(productoCategoria.getIdCategorias());
        //Ahora tenemos que hacer otro filtro idéntico al anterior, ya que realizamos una búsqueda
        //en BD con los ID's ingresados y eliminamos los que no existen.
        if(categorias.isEmpty()){
            throw new EmptyCollectionException("No hay categorías validas para agregar.");
        }
        //Obtenemos el tamaño de la lista de categorias de BD antes de agregar los nuevos
        // para utilizarlo como índice.
        int indiceCat = producto.getCategorias().size();
        //Ahora como ya tenemos categorias correctas toca actualizar el producto con las nuevas categorias.
        producto.setCategorias(fusionarListas(producto.getCategorias(), categorias));
        producto.getCategorias().forEach(p -> System.out.println("los ID's de las categorías es: " + p.getID()));

        //Ahora tenemos una lista con categorias ya existente y otras que no, pero puede que se eliminaron las nuevas,
        //porque eran repetidas de las existentes
        if(indiceCat == producto.getCategorias().size()){
            //Si el tamaño del índice inicial es igual al tamaño de la lista con los nuevos datos, regresamos una exception
            throw new EntityException("Agregue categorías diferentes a las existentes.");
        }
        proRe.save(producto);
        return proMa.paraProductoSimpleDTO(producto);
    }

    /**
     * Esta función servirá para eliminar categorias asociadas a un producto.
     * @param productoCategoria :Contendrá el ID del producto y los ID's de las categorías a eliminar.
     * @return :Regresará el mismo producto solo que con datos formateados.
     */
    @Override
    public ProductoSimpleDTO eliminarCategorias(ProductoCategoriaDTO productoCategoria) {
        //Validamos que el producto exista.
        Productos producto = proRe.findById(productoCategoria.getIdProducto())
                .orElseThrow(() -> new NotFoundEntityException("No se encontró producto con ese ID."));

        //Validación para saber si el usuario ingreso categorias para eliminar.
        if(productoCategoria.getIdCategorias().isEmpty()){
            //Si la lista de IDs para eliminar están vacíos entonces toca una exception.
            throw new EmptyCollectionException("Ingrese por lo menos una categoria para poder eliminarla.");
        }
        //Ahora validamos que el producto tenga categorias.
        if(producto.getCategorias().isEmpty()){
            //Si está vacía entonces regresamos una exception, porque no podremos borrar si no tiene nada.
            throw new EmptyCollectionException("El producto no tiene categorías para poder eliminar.");
        }
        //Ahora que tenemos la lista de categorias para eliminar toca eliminar las repetidas.
        List<Categorias> categorias = producto.getCategorias();

        categorias = eliminarCategoriasFunct(categorias, productoCategoria.getIdCategorias());

        //No podemos dejar que un producto se quede sin categorias, tenemos que tener una validación que
        //en donde confirme que el producto no puede quedarse sin categorias.
        if(categorias.isEmpty()){
            throw new EmptyCollectionException("El producto no se pude quedar sin categorías.");
        }
        //Ahora como la lista no está vacía toca actualizar.
        producto.setCategorias(categorias);
        producto = proRe.save(producto);
        return proMa.paraProductoSimpleDTO(producto);
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
     * Esta función sirve para combinar en una lista los valores de categorias que estaban registradas en BD y
     * las nuevas categorias que se quieren agregar, como tal sirve para eliminar duplicados.
     * @param categoriasDB :Lista con categorías de un producto con valores de BD
     * @param nuevasCategorias :Lista con categorías que ingreso el usuario.
     * @return : Regresará una lista con categorías que no se repiten entre ellas.
     */
    public List<Categorias> fusionarListas
            (List<Categorias> categoriasDB,List<Categorias> nuevasCategorias){
        //Creamos una lista que contendrá las categorias fijas
        List<Categorias> listaFiltrada = new ArrayList<>();

        //Para evitar ID duplicados creamos un Map y ahi guardaremos los ID's tanto de BD como los nuevos.
        Map<UUID, Categorias> mapCategorias = new HashMap<>();
        //Guardamos en un map para que no existan duplicados.
        //Recorremos la lista con datos de BD y lo formateamos en un Map.
        categoriasDB.forEach(db-> mapCategorias.put(db.getID(), db));
        //Recorremos la lista de las nuevas categorias y formateamos en el mismo map
        nuevasCategorias.forEach(nuevas -> mapCategorias.put(nuevas.getID(), nuevas));


        return new ArrayList<>(mapCategorias.values());
    }

    /**
     * Esta función sirve para eliminar categorias asociadas en un producto existente.
     * @param listaDB : Lista con las categorías de BD.
     * @param idEliminar :Lista de ID's que se eliminaran del producto.
     * @return : Lista que tendrá las categorías.
     */
    public List<Categorias> eliminarCategoriasFunct(List<Categorias> listaDB, Set<UUID> idEliminar){

        Map<UUID, Categorias> mapCategoria = new HashMap<>();
        //Ahora recorreremos la listaDB y lo meteremos en el mapa.
        listaDB.forEach(db -> mapCategoria.put(db.getID(), db));
        //Ahora como tenemos el ID de llave en el mapa, con el uso del Set, podremos comparar la llave de ambos
        //si encontramos una coincidencia la eliminamos.
        idEliminar.forEach(eliminar ->{
            if(mapCategoria.containsKey(eliminar)){
                //Si el mapa contiene de llave el ID de para eliminar entonces ahora si lo eliminamos del mapa.
                mapCategoria.remove(eliminar);
            }
        });
        return new ArrayList<>(mapCategoria.values());
    }

    /**
     * Esta funcionalidad servirá para eliminar un producto, solo que
     * lo podremos eliminar siempre y cuando este no tenga productos en
     * stock
     * @param ID : id del producto
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
