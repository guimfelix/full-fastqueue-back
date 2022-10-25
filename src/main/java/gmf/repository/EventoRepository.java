package gmf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gmf.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // @Query(value = "select e from evento e where ( e.nomeEvento ) like ( :nome
    // )")
    @Query("SELECT e FROM Evento e WHERE UPPER( e.nomeEvento ) LIKE UPPER( :nomeEvento ) ")
    List<Evento> findByNomeDoEvento(@Param("nomeEvento") String nomeEvento);

    List<Evento> findEventosByEspectadoresId(Long espectadorId);

    List<Evento> findEventosByProdutorId(Long produtorId);

}
