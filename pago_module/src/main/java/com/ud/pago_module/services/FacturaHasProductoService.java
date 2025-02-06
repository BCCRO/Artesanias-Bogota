package com.ud.pago_module.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;

import com.ud.pago_module.models.Factura;
import com.ud.pago_module.models.FacturaHasProducto;
import com.ud.pago_module.models.Producto;
import com.ud.pago_module.models.dtos.FacturaHasProductoDTO;
import com.ud.pago_module.repositories.FacturaHasProductoRepository;
import com.ud.pago_module.repositories.FacturaRepository;
import com.ud.pago_module.repositories.ProductoRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las relaciones entre Facturas y Productos.
 */
@Service
public class FacturaHasProductoService {

    @Autowired
    private FacturaHasProductoRepository facturaHasProductoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${servidor.loginModule}")
    private String loginModule;
    @Value("${servidor.inventarioModule}")
    private String inventarioModule;

    @Autowired
    private HttpServletRequest request;

    /**
     * Actualiza el total y los impuestos de una factura al agregar un producto.
     *
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @param cantidad La cantidad de productos añadidos.
     */
    private void actualizarTotalFactura(Long idFactura, Long idProducto, int cantidad) {
        Optional<Factura> facturaOpt = facturaRepository.findById(idFactura);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if (facturaOpt.isEmpty()) {
            throw new RuntimeException("Error actualizando la factura: " + idFactura);
        } else if (productoOpt.isEmpty()) {
            throw new RuntimeException("Error encontrando el producto: " + idProducto);
        } else {
            Factura factura = facturaOpt.get();
            Producto producto = productoOpt.get();
            Long totalAnterior = factura.getTotal();
            Long nuevoValor = totalAnterior + producto.getPrecioUnitario() * cantidad;
            factura.setTotal(nuevoValor);
            double impuestos = factura.getTotal() * 0.16;
            factura.setTotalImpuesto((long) impuestos);
        }
    }


    /**
     * Obtenemos el punto de venta mas cercanos al usuario de la factura
     * @param idFactura     id de la factura a la cual agregaremos el producto
     * @return              latitud y longitud del usuario
     */
    private Long getPuntoVentaCercano(Long idFactura){

        
        String token = (String) request.getAttribute("authtoken");
        Optional<Factura> facturaOpt = facturaRepository.findById(idFactura);
        if(facturaOpt.isPresent()){
            Factura factura = facturaOpt.get();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            /**
             * TODO necesitamos settear el token dinamicamnete
             */
            headers.setBearerAuth(token);
            String urlGetPuntosCercanos = String.format("%s/api/inventario/puntos_cercanos/%s", inventarioModule, factura.getIdUsuarioDocumento());

            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<List<String>> userResponse = restTemplate.exchange(urlGetPuntosCercanos, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<String>>() {});

            try {
                if (userResponse.getStatusCode() != HttpStatus.OK) {
                    throw new RuntimeException("Error al obtener el el puntto de venta mas cercano");
                }

                /**
                 * TODO, deberiamos validar la existencia en punto de venta
                 */
                List<String> arrayPuntosVenta = userResponse.getBody();
                return Long.parseLong(arrayPuntosVenta.get(0));

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        /**
         * todo Crear manejador de errores cuando no encontre la factura
         */
        return null;
    }


    /**
     * Añade un producto a una factura y actualiza el inventario del punto de venta.
     *
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @param cantidad La cantidad de productos añadidos.
     */
    public void anadirProductoFactura(Long idFactura, Long idProducto, int cantidad) {

        Long idPuntoVenta = getPuntoVentaCercano(idFactura);

        FacturaHasProducto facturaHasProducto = new FacturaHasProducto();
        facturaHasProducto.setIdFactura(idFactura);
        facturaHasProducto.setIdProducto(idProducto);
        facturaHasProducto.setCantidad(cantidad);
        facturaHasProductoRepository.save(facturaHasProducto);

        productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(idProducto, idPuntoVenta);
        actualizarTotalFactura(idFactura, idProducto, cantidad);
    }

    /**
     * Añade múltiples productos a una factura.
     * También actualiza los totales e inventarios correspondientes.
     *
     * @param listadoProductos Lista de productos a añadir.
     */
    public void anadirProductosFactura(List<FacturaHasProductoDTO> listadoProductos) {

        Long idPuntoVenta = null;

        Iterable<FacturaHasProducto> iterable = listadoProductos.stream()
                .map(dto -> {
                    productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(dto.getIdProducto(), idPuntoVenta);
                    actualizarTotalFactura(dto.getIdFactura(), dto.getIdProducto(), dto.getCantidad());
                    return new FacturaHasProducto(dto);
                })
                .collect(Collectors.toList());
        facturaHasProductoRepository.saveAll(iterable);
    }

    /**
     * Elimina un producto de una factura.
     *
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @return {@code true} si el producto fue eliminado correctamente, de lo contrario {@code false}.
     */
    public boolean removeItem(Long idFactura, Long idProducto) {
        if (!facturaRepository.existsById(idFactura)) {
            throw new IllegalArgumentException("La factura no existe", new Exception("404"));
        }
        if (!facturaHasProductoRepository.existsByIdFacturaAndIdProducto(idFactura, idProducto)) {
            throw new IllegalArgumentException("El producto no corresponde a esa factura", new Exception("404"));
        }

        FacturaHasProducto factura = facturaHasProductoRepository.findByIdFacturaAndIdProducto(idFactura, idProducto)
                .orElseThrow();
        try {
            facturaHasProductoRepository.delete(factura);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}