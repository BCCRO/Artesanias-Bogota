package com.ud.pago_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Clase principal para ejecutar la aplicación PagoModule.
 * Esta clase configura y lanza el contexto de la aplicación Spring Boot.
 */
@SpringBootApplication
@EnableAsync
public class PagoModuleApplication {

    /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(PagoModuleApplication.class, args);
    }

}