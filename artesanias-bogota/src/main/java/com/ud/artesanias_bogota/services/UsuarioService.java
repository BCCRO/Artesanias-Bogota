package com.ud.artesanias_bogota.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.query.NativeQuery.ReturnProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercadopago.resources.user.User;
import com.ud.artesanias_bogota.models.Rol;
import com.ud.artesanias_bogota.models.RolHasUsuario;
import com.ud.artesanias_bogota.models.Usuario;
import com.ud.artesanias_bogota.models.request.UserRequest;
import com.ud.artesanias_bogota.models.responses.RegisterResponse;
import com.ud.artesanias_bogota.models.responses.UserResponse;
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

  public List<UserResponse> getAllUsers(){
    return userRepo.findAll().stream().map(usuario -> UserResponse.builder()
    .name(usuario.getPrimerNombre() +" "+ usuario.getSegundoNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido())
    .documento(usuario.getDocumento())
    .email(usuario.getEmail())
    .fechaCreacion(usuario.getFechaCreacion())
    .fechaNacimiento(usuario.getFechaNacimiento())
    .direccion(usuario.getDireccion()).telefono(usuario.getTelefono())
    .roles(usuario.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList())
    .build()).collect(Collectors.toList());

  }

  public UserResponse getUser(String id){
    try {
      Usuario usuario = userRepo.findById(id).orElseThrow();
      return UserResponse.builder()
      .name(usuario.getPrimerNombre() +" "+ usuario.getSegundoNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido())
      .documento(usuario.getDocumento())
      .email(usuario.getEmail())
      .fechaCreacion(usuario.getFechaCreacion())
      .fechaNacimiento(usuario.getFechaNacimiento())
      .direccion(usuario.getDireccion()).telefono(usuario.getTelefono())
      .roles(usuario.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList())
      .build();
    } catch (Exception e) {
      return UserResponse.builder().name("User not found").build();
    }
    
  }
  
  public RegisterResponse create(UserRequest request){
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

  @Transactional
  public UserResponse updateUser(String id,UserRequest request){
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
            return UserResponse.builder()
                .name(buildFullName(usuario))
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
  
  public boolean deleteUser(String id){
    try {
      Usuario user = userRepo.findById(id).orElseThrow();
      userRepo.delete(user);
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
