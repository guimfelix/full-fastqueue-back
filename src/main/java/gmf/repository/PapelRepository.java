package gmf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gmf.model.EPapel;
import gmf.model.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
  Optional<Papel> findByName(EPapel name);
}
