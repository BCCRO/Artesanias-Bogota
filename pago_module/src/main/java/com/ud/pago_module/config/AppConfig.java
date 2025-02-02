package com.ud.pago_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración general para la aplicación.
 * Proporciona configuraciones específicas, como la gestión de codificación de contraseñas.
 */
@Configuration // Indica que esta clase contiene configuraciones de Spring.
public class AppConfig {

  /**
   * Define un bean para codificar contraseñas utilizando BCrypt.
   * 
   * @return Una instancia de PasswordEncoder que utiliza BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Implementación de codificación BCrypt para contraseñas.
  }

  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("MyAsyncThread-");
    executor.setRejectedExecutionHandler((r, executor1) -> System.out.println("Task rejected, thread pool is full and queue is also full"));
    executor.initialize();
    return executor;
  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
