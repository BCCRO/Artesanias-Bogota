package com.ud.report_module.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global para el manejo de CORS en la aplicación.
 * Permite configurar las reglas para el intercambio de recursos entre dominios.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configura las políticas de CORS para permitir solicitudes de orígenes cruzados.
     * 
     * @param registry el registro utilizado para agregar configuraciones de mapeo de CORS.
     */
    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permite solicitudes de cualquier origen ("**") con todos los métodos HTTP permitidos ("*").
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*");
  }
}
