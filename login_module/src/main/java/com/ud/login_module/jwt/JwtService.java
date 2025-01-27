package com.ud.login_module.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio para la generación de tokens JWT.
 * Proporciona métodos para crear y firmar tokens basados en la información del usuario.
 */
@Service // Marca esta clase como un servicio de Spring para ser gestionada como un bean.
public class JwtService {

  // Clave secreta utilizada para firmar los tokens JWT.
  private static final String SECRET_KEY = "8e5a564ea09a55809097ac5fc4fc81e38a1c9cb3cea15a23b9f2c39789191ceab1ebb7993973dfef28417fe460317f772f4094b743820edaf81bfe357cbe4104";

  /**
   * Genera un token JWT para el usuario proporcionado.
   * 
   * @param user Un objeto UserDetails que representa al usuario.
   * @return Un token JWT firmado.
   */
  public String getToken(UserDetails user) {
    // Llama al método sobrecargado con un mapa vacío de claims adicionales.
    return getToken(new HashMap<>(), user);
  }

  /**
   * Genera un token JWT con claims adicionales para el usuario proporcionado.
   * 
   * @param extraClaims Un mapa con claims adicionales para incluir en el token.
   * @param user Un objeto UserDetails que representa al usuario.
   * @return Un token JWT firmado.
   */
  private String getToken(Map<String, Object> extraClaims, UserDetails user) {
    return Jwts
        .builder()
        .setClaims(extraClaims) // Agrega claims personalizados al token.
        .setSubject(user.getUsername()) // Establece el nombre de usuario como sujeto del token.
        .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión del token.
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Establece la fecha de expiración (24 minutos después de la emisión).
        .signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token utilizando la clave secreta y el algoritmo HS256.
        .compact(); // Construye el token como una cadena compacta.
  }

  /**
   * Obtiene la clave secreta utilizada para firmar los tokens JWT.
   * 
   * @return Una instancia de Key basada en la clave secreta.
   */
  private Key getKey() {
    // Decodifica la clave secreta en base64 para usarla en la firma del token.
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes); // Genera una clave HMAC a partir de los bytes decodificados.
  }
}