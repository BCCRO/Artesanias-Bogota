package com.ud.inventario_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración general de la aplicación.
 * Esta clase define beans necesarios para el funcionamiento de la aplicación.
 */
@Configuration // Indica que esta clase contiene definiciones de beans para el contexto de Spring.
public class AppConfig {

  /**
   * Define el bean para el codificador de contraseñas.
   * Se utiliza BCryptPasswordEncoder para encriptar contraseñas de manera segura.
   * 
   * @return una instancia de PasswordEncoder basada en BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Devuelve una implementación de PasswordEncoder que utiliza el algoritmo BCrypt.
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
}
