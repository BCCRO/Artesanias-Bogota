package com.ud.artesanias_bogota.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagoService {

    public PagoService() {
        MercadoPagoConfig.setAccessToken("APP_USR-1559532781124737-110509-afae2c6da54175fa335091864c35ca06-2079614828");
    }

    public Preference createPreference() throws MPException, MPApiException {

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

}
