package com.ud.login_module.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ud.login_module.User.Usuario;
import com.ud.login_module.models.dtos.UsuarioDTO;
import com.ud.login_module.models.responses.RegisterResponse;
import com.ud.login_module.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
/**
 * Servicio para la gestión de usuarios.
 * Proporciona funcionalidades como crear, actualizar y listar usuarios, además de manejar roles y estados.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final PasswordEncoder passEncode;
  private final UsuarioRepository userRepo;
//  private final RolHasUserRepository userRolRepo;

  /**
   * Obtiene una lista de todos los usuarios registrados en el sistema.
   * 
   * @return lista de usuarios en formato DTO.
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
              .rol(usuario.getIdRol())
//        .roles(usuario.getAuthorities()
//          .stream()
//          .map(GrantedAuthority::getAuthority)
//          .toList())
        .build())
      .collect(Collectors.toList());
  }

  /**
   * Obtiene un usuario específico por su documento.
   * 
   * @param id documento del usuario.
   * @return usuario en formato DTO.
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
              .rol(usuario.getIdRol())
//        .roles(usuario.getAuthorities()
//          .stream()
//          .map(GrantedAuthority::getAuthority)
//          .toList())
        .build();
    } catch (Exception e) {
      return UsuarioDTO.builder().primerNombre("Usuario no encontrado").build();
    }
  }

  /**
   * Crea un nuevo usuario en el sistema.
   * 
   * @param request datos del usuario a registrar.
   * @return respuesta de registro con detalles del resultado.
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
              .idRol(request.getRol())
        .activo(true)
        .build();

//      List<Rol> roles = request.getRoles().stream()
//        .map(rol -> rolRepo.findByRolIgnoreCase(rol)
//          .orElseThrow(() -> new IllegalArgumentException("Rol no existente: " + rol)))
//        .toList();

      userRepo.save(usuario);
//      roles.forEach(rol -> {
//        RolHasUsuario rolUsuario = new RolHasUsuario(usuario, rol);
//        userRolRepo.save(rolUsuario);
//      });

      return RegisterResponse.builder()
        .statusCode(200)
        .userId(usuario.getDocumento())
        .userName(buildFullName(usuario))
        .message("Usuario creado correctamente")
        .build();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return RegisterResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
        .build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return RegisterResponse.builder()
        .statusCode(500)
        .message("Ocurrió un error inesperado")
        .build();
    }
  }

  public RegisterResponse createCliente(UsuarioDTO request){
    try {
      // Validar si el documento ya está registrado
      if (userRepo.existsByDocumento(request.getDocumento())) {
        throw new IllegalArgumentException("Ya existe un usuario con el documento proporcionado");
      }
      // Validar si el email ya está registrado
      if (userRepo.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("Ya existe un usuario con el email proporcionado");
      }
      // Validar si el teléfono ya está registrado
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
              .idRol(2) 
              .activo(true)
              .build();
//      Rol cliente = rolRepo.findByRolIgnoreCase("cliente")
//              .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
//      List<Rol> roles = new ArrayList<>();
//      roles.add(cliente);
      userRepo.save(usuario);
//      roles.forEach(rol -> {
//        RolHasUsuario rolUsuario = new RolHasUsuario(usuario,rol);
//        userRolRepo.save(rolUsuario);
//      });

      return RegisterResponse.builder()
              .statusCode(200)
              .userId(usuario.getDocumento())
              .userName(buildFullName(usuario))
              .message("Usuario creado correctamente")
              .build();
    }catch(IllegalArgumentException e){
      return RegisterResponse.builder()
              .statusCode(409)
              .message(e.getMessage())
              .build();
    }catch (Exception e) {
      return RegisterResponse.builder()
              .statusCode(500)
              .userId(null)
              .userName(null)
              .message("Ocurrio un error inesperado")
              .build();
    }
  }

  /**
   * Actualiza la información de un usuario existente.
   * 
   * @param id      documento del usuario a actualizar.
   * @param request datos actualizados del usuario.
   * @return usuario actualizado en formato DTO.
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
    if (request.getRol() != null) usuario.setIdRol(request.getRol());
    if (request.getTelefono() != null) {
      if (userRepo.existsByTelefono(request.getTelefono())) {
        throw new IllegalArgumentException("Ya existe un usuario con el teléfono proporcionado");
      }
      usuario.setTelefono(request.getTelefono());
    }
    if (request.getDireccion() != null) usuario.setDireccion(request.getDireccion());
    if (request.getEmail() != null) {
      if (userRepo.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("Ya existe un usuario con el email proporcionado");
      }
      usuario.setEmail(request.getEmail());
    }
//    if (request.getRoles() != null) {
//      usuario.getRolesUsuario().forEach(rol -> userRolRepo.delete(rol));
//      List<Rol> roles = request.getRoles().stream()
//        .map(rol -> rolRepo.findByRolIgnoreCase(rol)
//          .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol)))
//        .toList();
//      roles.forEach(rol -> {
//        RolHasUsuario rolUsuario = new RolHasUsuario(usuario, rol);
//        userRolRepo.save(rolUsuario);
//      });
//    }
    userRepo.save(usuario);
    return UsuarioDTO.builder()
      .primerNombre(usuario.getPrimerNombre())
      .segundoNombre(usuario.getSegundoNombre())
      .primerApellido(usuario.getPrimerApellido())
      .segundoApellido(usuario.getSegundoApellido())
      .documento(usuario.getDocumento())
      .email(usuario.getEmail())
      .direccion(usuario.getDireccion())
            .rol(usuario.getIdRol())
//      .roles(usuario.getRolesUsuario().stream()
//        .map(rol -> rol.getRol().getRol())
//        .toList())
      .build();
  }

  /**
   * Cambia el estado activo/inactivo de un usuario.
   * 
   * @param id documento del usuario.
   * @return {@code true} si el cambio fue exitoso.
   */
  public boolean changeUserStatus(String id) {
    Usuario user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    user.chageStatus();
    userRepo.save(user);
    return true;
  }

  /**
   * Cambia la contraseña de un usuario
   * 
   * @param id documento del usuario
   * @param password la antigua contraseña del usuario
   * @param newPassword la nueva contraseña del usuario
   * @return {@code true} si el cambio de contraseña fue exitoso.
   */

  public boolean updatePassword(String id, String oldPassword, String newPassword){
    Usuario user = userRepo.findById(id).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
    if(!(passEncode.matches(user.getContrasenia(), oldPassword))) throw new IllegalArgumentException("La contraseña antigua no coincide");
    user.setContrasenia(passEncode.encode(newPassword));
    userRepo.save(user);
    return true;
  }

  /**
   * Construye el nombre completo de un usuario.
   * 
   * @param usuario usuario del que se generará el nombre completo.
   * @return nombre completo en formato de cadena.
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