package gmf.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gmf.model.Espectador;
import gmf.model.Usuario;
import gmf.repository.EspectadorRepository;
import gmf.repository.UsuarioRepository;

@Component
public class EspectadorDao {

    @Autowired
    private final EspectadorRepository repository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public EspectadorDao(EspectadorRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Espectador salvarEspectador(Espectador espectador) {
        Usuario user = usuarioRepository.findById(espectador.getUsuario().getId()).orElseThrow();
        user.setIsCadastrado(true);
        usuarioRepository.save(user);

        return repository.save(espectador);
    }

    @Transactional
    public Espectador buscaEspectadorPorUsuarioId(Long id) {
        // Usuario user = usuarioRepository.findById(id).orElseThrow();
        System.out.println(">>>>> usuario");
        return repository.findByUsuario(id);
    }

}
