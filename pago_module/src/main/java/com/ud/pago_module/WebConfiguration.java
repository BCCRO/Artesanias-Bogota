package com.ud.pago_module;

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
     * Configura las reglas CORS para todos los endpoints de la aplicación.
     *
     * @param registry Objeto {@link CorsRegistry} utilizado para definir las reglas de CORS.
     */
    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        // Permitir todas las solicitudes desde cualquier origen y con cualquier método HTTP.
        registry.addMapping("/**")
                .allowedOrigins("https://artesaniasbogota-frontend.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "Accept")
                .allowCredentials(true);
    }
}
