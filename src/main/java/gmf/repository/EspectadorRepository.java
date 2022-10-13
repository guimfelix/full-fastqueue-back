package gmf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Espectador;

public interface EspectadorRepository extends JpaRepository<Espectador, Long> {

    Espectador findByUsuario(Long id);

}
