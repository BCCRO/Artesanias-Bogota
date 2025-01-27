package com.ud.inventario_module;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración personalizada para el manejo de CORS (Cross-Origin Resource Sharing) en la aplicación.
 * 
 * Esta clase implementa la interfaz {@link WebMvcConfigurer} para permitir configuraciones específicas relacionadas
 * con las solicitudes HTTP entre diferentes dominios.
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
        registry.addMapping("/**").allowedMethods("*");
    }
}