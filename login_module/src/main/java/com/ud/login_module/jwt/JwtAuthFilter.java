package com.ud.login_module.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por cada solicitud.
 * Se utiliza para extraer y procesar tokens JWT desde las solicitudes HTTP.
 */
@Component // Marca esta clase como un componente de Spring para que sea gestionada por el contenedor.
public class JwtAuthFilter extends OncePerRequestFilter {

  /**
   * Método principal del filtro que se ejecuta para cada solicitud HTTP.
   *
   * @param request Objeto HttpServletRequest que representa la solicitud HTTP.
   * @param response Objeto HttpServletResponse que representa la respuesta HTTP.
   * @param filterChain Cadena de filtros que permite continuar con el siguiente filtro o el controlador.
   * @throws ServletException Si ocurre un error en el procesamiento del filtro.
   * @throws IOException Si ocurre un error de entrada/salida.
   */
  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Obtiene el token JWT desde la solicitud.
    final String token = getTokenFromRequest(request);

    // Si no se encuentra el token, redirige a otra URL y termina el filtro.
    if (token == null) {
      filterChain.doFilter(request, response); // Continúa con el siguiente filtro en la cadena.
      response.encodeRedirectURL("https://github.com"); // Redirige a la URL especificada.
      return;
    }

    // Si el token existe, continúa con el siguiente filtro o controlador.
    filterChain.doFilter(request, response);
  }

  /**
   * Extrae el token JWT del encabezado de autorización de la solicitud HTTP.
   *
   * @param request Objeto HttpServletRequest que representa la solicitud HTTP.
   * @return El token JWT si está presente, de lo contrario, retorna null.
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    // Obtiene el encabezado de autorización de la solicitud.
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Verifica si el encabezado es válido y comienza con "Bearer".
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {
      // Retorna el token sin el prefijo "Bearer".
      return authHeader.substring(7);
    }
    return null; // Retorna null si no se encuentra un token válido.
  }
}