package gmf.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Conteudo publico.";
  }

  @GetMapping("/espectador")
  @PreAuthorize("hasRole('ESPECTADOR') or hasRole('PRODUTOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "Conteudo de espectador.";
  }

  @GetMapping("/produtor")
  @PreAuthorize("hasRole('PRODUTOR')")
  public String moderatorAccess() {
    return "Ambiente de produtor.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Ambiente de administrador.";
  }
}
