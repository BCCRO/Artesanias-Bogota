package com.ud.artesanias_bogota.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PagoService {

    @Autowired
    private FacturaService facturaService;

    public PagoService() {
        MercadoPagoConfig.setAccessToken("APP_USR-1559532781124737-110509-afae2c6da54175fa335091864c35ca06-2079614828");
    }

    public Preference createTestPreference() throws MPException, MPApiException {

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("1234")
                        .title("ProdPrueba")
                        .description("prueba")
//                        .pictureUrl("http://picture.com/PS5")
                        .categoryId("prueba")
                        .quantity(1)
                        .currencyId("COP")
                        .unitPrice(new BigDecimal("1000"))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://127.0.0.1:8081/pruebaPago/pruebaPago.html")
                .pending("http://127.0.0.1:8081/pruebaPago/pruebaPago.html")
                .failure("http://127.0.0.1:8081/pruebaPago/pruebaPago.html")
                .build();

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("idFactura", "987654321");
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(preferenceBackUrlsRequest)
                .notificationUrl("https://0f28-191-109-68-23.ngrok-free.app/api/pagos/webhook/prueba")
                .metadata(metadataMap)
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);
        System.out.println(preference.getId());
        return preference;
    }

    private List<PreferenceItemRequest> crearListadoProductosByFactura(Long idFactura){
        List<PreferenceItemRequest> items = new ArrayList<>();

        Optional<Factura> factura = facturaService.obtenerFactura(idFactura);
        if(factura.isPresent()){
            factura.get().getProductosFacturas().forEach(facturaHasProducto -> {
                Producto producto = facturaHasProducto.getProducto();

                PreferenceItemRequest itemRequest =
                        PreferenceItemRequest.builder()
                                .id(Long.toString(producto.getId()))    //TODO necesitamos setear el id?
                                .title(producto.getNombre())
                                .description(producto.getDescripcion())
//                              .pictureUrl(productto.getImagen())      //TODO
                                .categoryId(producto.getCategoriaProducto().getNombre())
                                .quantity(facturaHasProducto.getCantidad())
                                .currencyId("COP")
                                .unitPrice(BigDecimal.valueOf(producto.getPrecioUnitario()))
                                .build();
                items.add(itemRequest);
            });
        }

        return items;
    }

    public Preference createPreferenceByFactura(Long idFactura) throws MPException, MPApiException {

        List<PreferenceItemRequest> items = crearListadoProductosByFactura(idFactura);

        if(items.isEmpty()) return null;

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        System.out.println(String.format("Se ha creado la factura con id %s de la factura %s", preference.getId(), idFactura));   //TODO utilizar logger
        return preference;
    }

}
