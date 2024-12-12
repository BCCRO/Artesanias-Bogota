package com.ud.artesanias_bogota.services;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ud.artesanias_bogota.models.Rol;
import com.ud.artesanias_bogota.models.RolHasUsuario;
import com.ud.artesanias_bogota.models.Usuario;
import com.ud.artesanias_bogota.models.dtos.UsuarioDTO;
import com.ud.artesanias_bogota.models.responses.RegisterResponse;
import com.ud.artesanias_bogota.repositories.RolHasUserRepository;
import com.ud.artesanias_bogota.repositories.RolRepository;
import com.ud.artesanias_bogota.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final PasswordEncoder passEncode;
  private final UsuarioRepository userRepo;
  private final RolRepository rolRepo;
  private final RolHasUserRepository userRolRepo;

  public List<UsuarioDTO> getAllUsers(){
    return userRepo.findAll().stream().map(usuario -> UsuarioDTO.builder()
    .primerNombre(usuario.getPrimerNombre())
    .segundoNombre(usuario.getSegundoNombre())
    .primerApellido(usuario.getPrimerApellido())
    .segundoApellido(usuario.getSegundoApellido())
    .documento(usuario.getDocumento())
    .email(usuario.getEmail())
    .fechaCreacion(usuario.getFechaCreacion())
    .fechaNacimiento(usuario.getFechaNacimiento())
    .direccion(usuario.getDireccion()).telefono(usuario.getTelefono())
    .isActive(usuario.isActivo())
    .roles(usuario.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList())
    .build()).collect(Collectors.toList());

  }

  public UsuarioDTO getUser(String id){
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
      .stream().map(GrantedAuthority:: getAuthority).toList())
      .build();
    } catch (Exception e) {
      return UsuarioDTO.builder()
      .primerNombre("Usuario no encontrado").build();
    }
    
  }
  
  public RegisterResponse create(UsuarioDTO request){
    try {
      if (!userRepo.findById(request.getDocumento()).isEmpty()){
        throw new RuntimeException("Usuario ya existente en la base de datos");
      }
      System.out.println(request.getRoles());
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
      List<Rol> roles = request.getRoles().stream().map(rol -> rolRepo.findByRolIgnoreCase(rol).orElseThrow(()-> new RuntimeException("Rol no encontrado: "+rol)))
      .toList();
      userRepo.save(usuario);
      roles.forEach(rol -> {
        RolHasUsuario rolUsuario = new RolHasUsuario(usuario,rol);
        userRolRepo.save(rolUsuario);
      });
      
      return RegisterResponse.builder()
        .statusCode(200)
        .userId(usuario.getDocumento())
        .userName(buildFullName(usuario))
        .message("Usuario creado correctamente")
        .build();
    } catch (Exception e) {
      if (e.getMessage().equals("Usuario ya existente en la base de datos")) {
        return RegisterResponse.builder()
        .statusCode(409)
        .message("Ya existe un usuario con el mismo documento ingresado")
        .e(e)
        .build();
      }
      return RegisterResponse.builder()
        .statusCode(500)
        .userId(null)
        .userName(null)
        .message("Something went wrong")
        .e(e)
        .build();
      }
  }

  public RegisterResponse createCliente(UsuarioDTO request){
    try {
      if (!userRepo.findById(request.getDocumento()).isEmpty()){
        throw new RuntimeException("Usuario ya existente en la base de datos");
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
      Rol cliente = rolRepo.findByRolIgnoreCase("cliente")
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
      List<Rol> roles = new ArrayList<>();
      roles.add(cliente);
      userRepo.save(usuario);
      roles.forEach(rol -> {
        RolHasUsuario rolUsuario = new RolHasUsuario(usuario,rol);
        userRolRepo.save(rolUsuario);
      });
      
      return RegisterResponse.builder()
        .statusCode(200)
        .userId(usuario.getDocumento())
        .userName(buildFullName(usuario))
        .message("Usuario creado correctamente")
        .build();
    } catch (Exception e) {
      if (e.getMessage().equals("Usuario ya existente en la base de datos")) {
        return RegisterResponse.builder()
        .statusCode(409)
        .message("Ya existe un usuario con el mismo documento ingresado")
        .e(e)
        .build();
      }
      return RegisterResponse.builder()
        .statusCode(500)
        .userId(null)
        .userName(null)
        .message("Something went wrong")
        .e(e)
        .build();
      }
  }


  @Transactional
  public UsuarioDTO updateUser(String id,UsuarioDTO request){
      Usuario usuario = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            if (request.getPrimerNombre() != null) {
                usuario.setPrimerNombre(request.getPrimerNombre());
            }
            if (request.getSegundoNombre() != null) {
                usuario.setSegundoNombre(request.getSegundoNombre());
            }
            if (request.getPrimerApellido() != null) {
                usuario.setPrimerApellido(request.getPrimerApellido());
            }
            if (request.getSegundoApellido() != null) {
                usuario.setSegundoApellido(request.getSegundoApellido());
            }
            if (request.getFechaNacimiento() != null) {
                usuario.setFechaNacimiento(request.getFechaNacimiento());
            }
            if (request.getTelefono() != null) {
                usuario.setTelefono(request.getTelefono());
            }
            if (request.getDireccion() != null) {
                usuario.setDireccion(request.getDireccion());
            }
            if (request.getEmail() != null) {
                usuario.setEmail(request.getEmail());
            }
            if (request.getRoles() != null) {
                usuario.getRolesUsuario().stream().forEach(rol->userRolRepo.delete(rol));
                usuario.getRolesUsuario().clear();

                List<Rol> roles = request.getRoles().stream()
                .map(rol -> rolRepo.findByRolIgnoreCase(rol)
                  .orElseThrow(()-> new RuntimeException("Rol no encontrado:"+rol)))
                .toList();

                roles.forEach(rol -> {
                    RolHasUsuario rolHasUsuario = new RolHasUsuario(usuario, rol);
                    usuario.getRolesUsuario().add(rolHasUsuario);
                    userRolRepo.save(rolHasUsuario);
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
                    .map(rolHasUsuario -> rolHasUsuario.getRol().getRol())
                    .collect(Collectors.toList()))
                .build();
  }
  
  public boolean changeUserStatus(String id){
    try {
      Usuario user = userRepo.findById(id).orElseThrow();
      user.chageStatus();
      userRepo.save(user);
      return true;
    } catch (Exception e) {
      return false;
    }


  }

  private String buildFullName(Usuario usuario) {
        return String.join(" ",
            usuario.getPrimerNombre(),
            usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() : "",
            usuario.getPrimerApellido(),
            usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : ""
        ).trim();
    }
}
