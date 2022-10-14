package gmf.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmf.model.EPapel;
import gmf.model.Papel;
import gmf.model.Usuario;
import gmf.payload.request.LoginRequest;
import gmf.payload.request.SignupRequest;
import gmf.payload.response.JwtResponse;
import gmf.payload.response.MessageResponse;
import gmf.repository.PapelRepository;
import gmf.repository.UsuarioRepository;
import gmf.security.jwt.JwtUtils;
import gmf.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuarioRepository userRepository;

  @Autowired
  PapelRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles,
        userDetails.getIsCadastrado()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Erro: Username já existe!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Erro: Email ja em uso"));
    }

    // Create new user's account
    Usuario user = new Usuario(signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()), Boolean.FALSE);

    Set<String> strRoles = signUpRequest.getRole();
    Set<Papel> roles = new HashSet<>();

    if (strRoles == null) {
      Papel userRole = roleRepository.findByName(EPapel.PAPEL_ESPECTADOR)
          .orElseThrow(() -> new RuntimeException("Erro: Papel não encontrado."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Papel adminRole = roleRepository.findByName(EPapel.PAPEL_ADMIN)
                .orElseThrow(() -> new RuntimeException("Erro: Papel não encontrado."));
            roles.add(adminRole);

            break;
          case "produtor":
            Papel modRole = roleRepository.findByName(EPapel.PAPEL_PRODUTOR)
                .orElseThrow(() -> new RuntimeException("Erro: Papel não encontrado."));
            roles.add(modRole);

            break;
          default:
            Papel userRole = roleRepository.findByName(EPapel.PAPEL_ESPECTADOR)
                .orElseThrow(() -> new RuntimeException("Erro: Papel não encontrado."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    System.out.println(">>>>" + user);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Usuario registrado com sucesso!"));
  }
}
