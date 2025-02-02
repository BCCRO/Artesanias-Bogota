package com.ud.pago_module.services;

import com.ud.pago_module.models.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PedidoAsyncService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${servidor.inventarioModule}")
    private String inventarioModule;

    @Value("${servidor.loginModule}")
    private String loginModule;

    @Async
    public void updateStateAutomatically(Factura factura, Long idPuntosVenta) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlGetUsuarios = String.format("%s/api/usuarios?id=%s", loginModule, factura.getUsuarioDocumento());

        ResponseEntity<String> userResponse = restTemplate.getForEntity(urlGetUsuarios, String.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idFactura", factura.getId());
        requestBody.put("idPuntosVenta", 1);        //todo quemado
        requestBody.put("latEntrega", idFactura);
        requestBody.put("longEntrega", idFactura);

        try {
            Thread.sleep(180000);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(inventarioModule+"/api/pedidos/create-pedido", HttpMethod.POST, requestEntity, String.class);

            System.out.println(response);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
