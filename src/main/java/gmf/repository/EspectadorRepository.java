package gmf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Espectador;
import gmf.model.Usuario;

public interface EspectadorRepository extends JpaRepository<Espectador, Long> {

    Espectador findByUsuario(Usuario usuario);

    List<Espectador> findEspectadorByEventosId(Long eventoId);

}
