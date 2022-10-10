package gmf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

}
