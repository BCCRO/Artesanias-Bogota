package com.ud.login_module.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ud.login_module.User.Rol;
import com.ud.login_module.User.RolHasUsuario;
import com.ud.login_module.User.Usuario;
import com.ud.login_module.models.dtos.UsuarioDTO;
import com.ud.login_module.models.responses.RegisterResponse;
import com.ud.login_module.repositories.RolHasUserRepository;
import com.ud.login_module.repositories.RolRepository;
import com.ud.login_module.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para la gestión de usuarios.
 * Proporciona métodos para CRUD de usuarios, asignación de roles, y actualización de estado.
 */
@Service
@RequiredArgsConstructor // Genera un constructor para los campos finales declarados.
public class UsuarioService {

  private final PasswordEncoder passEncode;
  private final UsuarioRepository userRepo;
  private final RolRepository rolRepo;
  private final RolHasUserRepository userRolRepo;

  /**
   * Obtiene una lista de todos los usuarios en formato DTO.
   *
   * @return Una lista de usuarios con sus datos principales.
   */
  public List<UsuarioDTO> getAllUsers() {
    return userRepo.findAll().stream()
        .map(usuario -> UsuarioDTO.builder()
            .primerNombre(usuario.getPrimerNombre())
            .segundoNombre(usuario.getSegundoNombre())
            .primerApellido(usuario.getPrimerApellido())
            .segundoApellido(usuario.getSegundoApellido())
            .documento(usuario.getDocumento())
            .email(usuario.getEmail())
            .fechaCreacion(usuario.getFechaCreacion())
            .fechaNacimiento(usuario.getFechaNacimiento())
            .direccion(usuario.getDireccion())
            .telefono(usuario.getTelefono())
            .isActive(usuario.isActivo())
            .roles(usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList())
            .build())
        .collect(Collectors.toList());
  }

  /**
   * Obtiene los datos de un usuario por su ID.
   *
   * @param id El identificador del usuario.
   * @return Un DTO con los datos del usuario, o un mensaje si no se encuentra.
   */
  public UsuarioDTO getUser(String id) {
    try {
      Usuario usuario = userRepo.findById(id).orElseThrow();
      return UsuarioDTO.builder()
          .primerNombre(usuario.getPrimerNombre())
          .primerApellido(usuario.getPrimerApellido())
          .segundoNombre(usuario.getSegundoNombre())
          .segundoApellido(usuario.getSegundoApellido())
          .documento(usuario.getDocumento())
          .email(usuario.getEmail())
          .fechaCreacion(usuario.getFechaCreacion())
          .fechaNacimiento(usuario.getFechaNacimiento())
          .direccion(usuario.getDireccion())
          .telefono(usuario.getTelefono())
          .isActive(usuario.isActivo())
          .roles(usuario.getAuthorities()
              .stream().map(GrantedAuthority::getAuthority).toList())
          .build();
    } catch (Exception e) {
      return UsuarioDTO.builder().primerNombre("Usuario no encontrado").build();
    }
  }

  /**
   * Crea un nuevo usuario con los datos proporcionados.
   *
   * @param request Un DTO con los datos del nuevo usuario.
   * @return Una respuesta indicando el resultado del registro.
   */
  public RegisterResponse create(UsuarioDTO request) {
    try {
      if (userRepo.existsByDocumento(request.getDocumento())) {
        throw new IllegalArgumentException("Ya existe un usuario con el documento proporcionado");
      }
      if (userRepo.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("Ya existe un usuario con el email proporcionado");
      }
      if (userRepo.existsByTelefono(request.getTelefono())) {
        throw new IllegalArgumentException("Ya existe un usuario con el teléfono proporcionado");
      }

      Usuario usuario = Usuario.builder()
          .documento(request.getDocumento())
          .fechaNacimiento(request.getFechaNacimiento())
          .telefono(request.getTelefono())
          .primerNombre(request.getPrimerNombre())
          .segundoNombre(request.getSegundoNombre())
          .primerApellido(request.getPrimerApellido())
          .segundoApellido(request.getSegundoApellido())
          .fechaCreacion(request.getFechaCreacion())
          .direccion(request.getDireccion())
          .contrasenia(passEncode.encode(request.getContrasenia()))
          .email(request.getEmail())
          .activo(true)
          .build();

      List<Rol> roles = request.getRoles().stream()
          .map(rol -> rolRepo.findByRolIgnoreCase(rol)
              .orElseThrow(() -> new IllegalArgumentException("Rol no existente: " + rol)))
          .toList();

      userRepo.save(usuario);
      roles.forEach(rol -> {
        RolHasUsuario rolUsuario = new RolHasUsuario(usuario, rol);
        userRolRepo.save(rolUsuario);
      });

      return RegisterResponse.builder()
          .statusCode(200)
          .userId(usuario.getDocumento())
          .userName(buildFullName(usuario))
          .message("Usuario creado correctamente")
          .build();
    } catch (IllegalArgumentException e) {
      return RegisterResponse.builder()
          .statusCode(409)
          .message(e.getMessage())
          .build();
    } catch (Exception e) {
      return RegisterResponse.builder()
          .statusCode(500)
          .userId(null)
          .userName(null)
          .message("Ocurrió un error inesperado")
          .build();
    }
  }

  /**
   * Actualiza los datos de un usuario existente.
   *
   * @param id      El identificador del usuario.
   * @param request Los datos actualizados.
   * @return Un DTO con los datos del usuario actualizado.
   */
  @Transactional
  public UsuarioDTO updateUser(String id, UsuarioDTO request) {
    Usuario usuario = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    if (request.getPrimerNombre() != null) usuario.setPrimerNombre(request.getPrimerNombre());
    if (request.getSegundoNombre() != null) usuario.setSegundoNombre(request.getSegundoNombre());
    if (request.getPrimerApellido() != null) usuario.setPrimerApellido(request.getPrimerApellido());
    if (request.getSegundoApellido() != null) usuario.setSegundoApellido(request.getSegundoApellido());
    if (request.getFechaNacimiento() != null) usuario.setFechaNacimiento(request.getFechaNacimiento());
    if (request.getTelefono() != null && !userRepo.existsByTelefono(request.getTelefono())) {
      usuario.setTelefono(request.getTelefono());
    } else if (request.getTelefono() != null) {
      throw new IllegalArgumentException("Ya existe un usuario con el teléfono proporcionado");
    }
    if (request.getEmail() != null && !userRepo.existsByEmail(request.getEmail())) {
      usuario.setEmail(request.getEmail());
    } else if (request.getEmail() != null) {
      throw new IllegalArgumentException("Ya existe un usuario con el email proporcionado");
    }

    // Actualiza los roles si son proporcionados.
    if (request.getRoles() != null) {
      usuario.getRolesUsuario().clear();
      List<Rol> roles = request.getRoles().stream()
          .map(rol -> rolRepo.findByRolIgnoreCase(rol)
              .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol)))
          .toList();
      roles.forEach(rol -> {
        RolHasUsuario rolUsuario = new RolHasUsuario(usuario, rol);
        usuario.getRolesUsuario().add(rolUsuario);
        userRolRepo.save(rolUsuario);
      });
    }

    userRepo.save(usuario);

    return UsuarioDTO.builder()
        .primerNombre(usuario.getPrimerNombre())
        .primerApellido(usuario.getPrimerApellido())
        .segundoNombre(usuario.getSegundoNombre())
        .segundoApellido(usuario.getSegundoApellido())
        .documento(usuario.getDocumento())
        .email(usuario.getEmail())
        .fechaCreacion(usuario.getFechaCreacion())
        .fechaNacimiento(usuario.getFechaNacimiento())
        .direccion(usuario.getDireccion())
        .telefono(usuario.getTelefono())
        .roles(usuario.getRolesUsuario().stream()
            .map(rolUsuario -> rolUsuario.getRol().getRol())
            .collect(Collectors.toList()))
        .build();
  }

  /**
   * Cambia el estado de actividad de un usuario.
   *
   * @param id El identificador del usuario.
   * @return true si el estado fue cambiado exitosamente.
   */
  public boolean changeUserStatus(String id) {
    Usuario user = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    user.chageStatus();
    userRepo.save(user);
    return true;
  }

  /**
   * Construye el nombre completo de un usuario.
   *
   * @param usuario El usuario cuyos nombres serán combinados.
   * @return Una cadena con el nombre completo del usuario.
   */
  private String buildFullName(Usuario usuario) {
    return String.join(" ",
        usuario.getPrimerNombre(),
        usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() : "",
        usuario.getPrimerApellido(),
        usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : "")
        .trim();
  }
}