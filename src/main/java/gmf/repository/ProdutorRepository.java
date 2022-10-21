package gmf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gmf.model.Produtor;
import gmf.model.Usuario;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    Produtor findByUsuario(Usuario usuario);
}
