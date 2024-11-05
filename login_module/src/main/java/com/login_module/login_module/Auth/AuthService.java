package com.login_module.login_module.Auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.login_module.login_module.User.UserRepository;
import com.login_module.login_module.User.Usuario;
import com.login_module.login_module.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepo;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;

  public AuthResponse login(LoginRequest request) {
    
    authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    UserDetails user = userRepo.findByEmail(request.getEmail()).orElseThrow();
    String token = jwtService.getToken(user);

    return AuthResponse.builder()
    .token(token)
    .email(user.getUsername())
    .roles(getRoleNames(user)).build();
  }

  public AuthResponse register(RegisterRequest request){
    Usuario usuario = Usuario.builder()
    .documento(request.getDocumento())
    .fechaNacimiento(request.getFechaNacimiento())
    .telefono(request.getTelefono())
    .primerNombre(request.getPrimerNombre())
    .segundoNombre(request.getSegundoNombre())
    .primerApellido(request.getPrimerApellido())
    .segundoApellido(request.segundoApellido)
    .fechaCreacion(request.getFechaCreacion())
    .direccion(request.getDireccion())
    .contrasenia(passwordEncoder.encode(request.getContrasenia()))
    .email(request.getEmail())
    .build();

    userRepo.save(usuario);

    return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
  }

  private List<String> getRoleNames(UserDetails user){
    return user.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList();
  } 
}
