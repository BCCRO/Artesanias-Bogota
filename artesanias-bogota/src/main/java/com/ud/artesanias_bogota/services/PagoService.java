package com.ud.artesanias_bogota.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.models.Transaccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PagoService {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private TransaccionService transaccionService;

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
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();
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

    public Payment consultarPreference(Long id){

      PaymentClient client = new PaymentClient();
      try {
        Payment pago = client.get(id);
        Pattern pattern = Pattern.compile("\"metadata\":\\{[^}]*?\"factura_id\":\"([^\"]+)\"");
        Matcher match = pattern.matcher(pago.getResponse().getContent());
        match.find();
        String facturaId = match.group(1);
        if (facturaId == null) {
          throw new Exception("No se encontro el id de la factura");
        }
        System.out.println(facturaId);
        String estadoFactura = "";
        String estadoTransaccion = "";
        switch (pago.getStatus()) {
          case "approved":
            estadoFactura  = "C";
            estadoTransaccion = "C";
            break; 
          default:
            estadoFactura = "P";
            break;
        }
        facturaService.actualizarEstado(facturaId, estadoFactura);
        transaccionService.updateEstadoTransaccion( Long.parseLong(facturaId), estadoTransaccion);
        return pago;
      } catch (Exception e) {
        return new Payment();
      }
    }

    public Transaccion consultarEstado(Long id){
      //TODO: Consultar Estado transaccion
      Factura factura = facturaService.obtenerFactura(id).orElseThrow();
      Transaccion transaccion = transaccionService.getTransaccion(factura.getTransaccionId());
      try {
        return transaccion;
      } catch (Exception e) {
        throw new RuntimeException("Sucedio un error al consultar el estado");
      }
    }


}
