package com.ud.login_module.Auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ud.login_module.User.UserRepository;
import com.ud.login_module.User.Usuario;
import com.ud.login_module.error.ResponseException;
import com.ud.login_module.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service // Marca esta clase como un componente de servicio manejado por Spring.
@RequiredArgsConstructor // Genera un constructor que incluye todos los campos marcados como final.
public class AuthService {

  // Repositorio para manejar operaciones relacionadas con usuarios.
  private final UserRepository userRepo;

  // Servicio para la generación y validación de tokens JWT.
  private final JwtService jwtService;

  // Manejador de autenticación proporcionado por Spring Security.
  private final AuthenticationManager authManager;

  /**
   * Realiza el proceso de inicio de sesión para un usuario.
   *
   * @param request Un objeto LoginRequest que contiene las credenciales del usuario.
   * @return Un objeto AuthResponse que contiene información del usuario autenticado y su token.
   * @throws ResponseException Si el usuario no es encontrado o hay problemas en la autenticación.
   */
  public AuthResponse login(LoginRequest request) {
      // Verifica si el usuario existe en el repositorio.
      if (userRepo.findByEmail(request.getEmail()).isEmpty()) {
          System.out.println("User not found"); // Mensaje de depuración.
          throw new ResponseException("User not found", 404); // Lanza una excepción si no se encuentra el usuario.
      }

      // Autentica al usuario utilizando sus credenciales (email y contraseña).
      authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      // Obtiene al usuario desde el repositorio.
      Usuario user = userRepo.findByEmail(request.getEmail()).orElseThrow();

      // Genera un token JWT para el usuario autenticado.
      String token = jwtService.getToken(user);

      // Construye y retorna la respuesta de autenticación con los datos del usuario y el token.
      return AuthResponse.builder()
          .token(token)
          .primerNombre(user.getPrimerNombre())
          .primerApellido(user.getPrimerApellido())
          .segundoNombre(user.getSegundoNombre())
          .segundoApellido(user.getSegundoApellido())
          .documento(user.getDocumento())
          .email(user.getEmail())
          .fechaCreacion(user.getFechaCreacion())
          .fechaNacimiento(user.getFechaNacimiento())
          .direccion(user.getDireccion())
          .telefono(user.getTelefono())
          .isActive(user.isActivo())
          .roles(getRoleNames(user))
          .build();
  }

  /**
   * Obtiene los nombres de los roles asociados a un usuario.
   *
   * @param user Un objeto UserDetails que representa al usuario autenticado.
   * @return Una lista de cadenas con los nombres de los roles del usuario.
   */
  private List<String> getRoleNames(UserDetails user) {
      // Convierte las autoridades (GrantedAuthority) a una lista de nombres de roles.
      return user.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .toList();
  }
}