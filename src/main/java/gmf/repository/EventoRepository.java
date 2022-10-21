package gmf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gmf.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // @Query(value = "select e from evento e where ( e.nomeEvento ) like ( :nome
    // )")
    List<Evento> findByNomeEvento(String nomeEvento);

}
