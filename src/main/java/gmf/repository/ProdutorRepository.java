package gmf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Produtor;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    Produtor findByUsuario(Long id);
}
