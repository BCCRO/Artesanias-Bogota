package com.ud.inventario_module.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Servicio para la geocodificación de direcciones y cálculo de proximidad entre puntos geográficos.
 */
@Service
public class GeocodificacionService {

    // URL base de la API de OpenCage para obtener coordenadas geográficas
    private static final String API_URL = "https://api.opencagedata.com/geocode/v1/json";
    // Clave de API para acceder al servicio de OpenCage
    private static final String API_KEY = "48c98ea7490e4da2b3889d6098e4f0f1";

    /**
     * Obtiene las coordenadas geográficas (latitud y longitud) de una dirección dada.
     * 
     * @param address Dirección a geocodificar.
     * @return Arreglo con las coordenadas [latitud, longitud] o null si no se encuentran resultados.
     */
    public static double[] getCoordinates(String address) {
        try {
            // Formatear y codificar la dirección para la consulta HTTP
            String formattedAddress = formatAddress(address);
            String encodedAddress = URLEncoder.encode(formattedAddress, "UTF-8");

            // Construcción de la URL de la solicitud
            String requestUrl = API_URL + "?q=" + encodedAddress + "&key=" + API_KEY;
            URL url = new URI(requestUrl).toURL();

            // Establecer conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getJSONArray("results").isEmpty()) {
                System.out.println("No se encontraron resultados para la dirección proporcionada.");
                return null;
            }

            // Extraer las coordenadas geográficas del primer resultado
            JSONObject geometry = jsonResponse
                    .getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry");

            double latitude = geometry.getDouble("lat");
            double longitude = geometry.getDouble("lng");

            return new double[]{latitude, longitude};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Formatea una dirección agregando el país "Colombia" al final.
     * 
     * @param address Dirección a formatear.
     * @return Dirección formateada.
     */
    private static String formatAddress(String address) {
        return address.trim().replaceAll("\\s+", " ") + ", Colombia";
    }

    /**
     * Ordena una lista de puntos geográficos en función de su proximidad a un punto objetivo.
     * 
     * @param points Lista de puntos representados como arreglos de coordenadas [id, latitud, longitud].
     * @param targetPoint Punto objetivo con el cual comparar distancias [latitud, longitud].
     * @return Lista de identificadores ordenados por proximidad o null si la lista de puntos está vacía.
     */
    public static List<String> getSortedPointsByProximity(List<double[]> points, double[] targetPoint) {
        if (points == null || points.isEmpty() || targetPoint == null || targetPoint.length != 2) {
            return null;
        }

        // Ordenar los puntos en función de la distancia Haversine al punto objetivo
        points.sort(Comparator.comparingDouble(point -> haversineDistance(point[1], point[2], targetPoint[0], targetPoint[1])));

        // Extraer los identificadores de los puntos ordenados
        List<String> sortedIds = new ArrayList<>();
        for (double[] point : points) {
            sortedIds.add(String.valueOf((int) point[0]));
        }
        return sortedIds;
    }

    /**
     * Calcula la distancia Haversine entre dos puntos geográficos.
     * 
     * @param lat1 Latitud del primer punto.
     * @param lon1 Longitud del primer punto.
     * @param lat2 Latitud del segundo punto.
     * @param lon2 Longitud del segundo punto.
     * @return Distancia en kilómetros entre los dos puntos.
     */
    private static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Retorna la distancia en kilómetros
    }
}
