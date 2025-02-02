package com.ud.pago_module.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        Double latitud = null;
        Double longitud = null;
        try {
          if (userResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error al obtener el usuario");
          }

          String responseBody = userResponse.getBody();
          System.out.println("Respuesta JSON: " + responseBody);

          ObjectMapper objectMapper = new ObjectMapper();
          JsonNode rootNode = objectMapper.readTree(responseBody);

          // Extraer latitud y longitud
          latitud = rootNode.get("latitud").asDouble();
          longitud = rootNode.get("longitud").asDouble();

          System.out.println("Latitud: " + latitud);
          System.out.println("Longitud: " + longitud);
        } catch (Exception e) {
          // TODO: handle exception
        }
        

      


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idFactura", factura.getId());
        requestBody.put("idPuntosVenta", 1);        //todo quemado
        requestBody.put("latEntrega", latitud);
        requestBody.put("longEntrega", longitud);

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
