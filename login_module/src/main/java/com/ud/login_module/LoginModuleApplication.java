package com.ud.login_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Login Module.
 * Configura y lanza la aplicación Spring Boot.
 */
@SpringBootApplication // Marca esta clase como la configuración principal de la aplicación Spring Boot.
public class LoginModuleApplication {

    /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args Argumentos de línea de comandos (opcional).
     */
    public static void main(String[] args) {
        // Lanza la aplicación Spring Boot.
        SpringApplication.run(LoginModuleApplication.class, args);
    }
}