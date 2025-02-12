package com.ud.inventario_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Clase principal de la aplicación Artesanías Bogotá.
 * 
 * Esta clase es el punto de entrada para la ejecución de la aplicación Spring Boot.
 * Marca la configuración inicial y permite que Spring Boot configure automáticamente
 * todos los componentes necesarios para la ejecución del proyecto.
 */
@SpringBootApplication
@EnableAsync
public class ArtesaniasBogotaApplication {

    /**
     * Método principal para iniciar la aplicación.
     * 
     * @param args argumentos opcionales pasados al ejecutar la aplicación desde la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ArtesaniasBogotaApplication.class, args);
    }

}