package gmf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Espectador;
import gmf.model.Usuario;

public interface EspectadorRepository extends JpaRepository<Espectador, Long> {

    Espectador findByUsuario(Usuario usuario);

}
