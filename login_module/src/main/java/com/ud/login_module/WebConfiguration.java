package com.ud.login_module;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de la aplicación para manejar CORS (Cross-Origin Resource Sharing).
 * Permite el acceso desde diferentes orígenes a los endpoints de la aplicación.
 */
@Configuration // Indica que esta clase contiene configuraciones específicas de Spring.
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configura los mapeos CORS para la aplicación.
     *
     * @param registry Objeto CorsRegistry que permite definir reglas de CORS.
     */
    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.) para cualquier origen.
        registry.addMapping("/**")
                .allowedOrigins("https://artesaniasbogota-frontend.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "Accept")
                .allowCredentials(true);
    }
}