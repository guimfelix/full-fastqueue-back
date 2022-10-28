package gmf.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import gmf.dao.EspectadorDao;
import gmf.model.Espectador;
import gmf.model.Evento;
import gmf.model.Usuario;
import gmf.repository.EnderecoRepository;
import gmf.repository.EspectadorRepository;
import gmf.repository.EventoRepository;
import gmf.repository.UsuarioRepository;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/espectador")
public class EspectadorController {

    @Autowired
    private final EspectadorDao espectadorDao;
    @Autowired
    private final EspectadorRepository repository;
    @Autowired
    private final EventoRepository eventoRepository;
    @Autowired
    private final EnderecoRepository enderecoRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public EspectadorController(EspectadorDao espectadorDao, EspectadorRepository repository,
            UsuarioRepository usuarioRepository, EventoRepository eventoRepository,
            EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.espectadorDao = espectadorDao;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping
    public List<Espectador> obterTodos() {
        return repository.findAll();
    }

    @GetMapping("usuario/{id}")
    public Espectador obterPorUsuario(@PathVariable Long id) {
        return repository.findByUsuario(usuarioRepository.findById(id).orElseThrow());
    }

    @GetMapping("{id}/eventos")
    public List<Evento> obterEventosDeEspectador(@PathVariable Long id) {
        return eventoRepository.findEventosByEspectadoresId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Espectador salvar(@RequestBody @Valid Espectador espectador) {
        Usuario user = usuarioRepository.findById(espectador.getUsuario().getId()).orElseThrow();
        user.setIsCadastrado(true);
        espectador.setUsuario(user);
        usuarioRepository.save(user);
        enderecoRepository.save(espectador.endereco);
        return repository.save(espectador);
    }

    @GetMapping("{id}")
    public Espectador acharPorId(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        repository
                .findById(id)
                .map(espectador -> {
                    repository.delete(espectador);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id,
            @RequestBody @Valid Espectador espectadorAtualizado) {
        repository
                .findById(id)
                .map(espectador -> {
                    espectador.setNome(espectadorAtualizado.getNome());
                    espectador.setDataNascimento(espectadorAtualizado.getDataNascimento());
                    espectador.setEndereco(espectadorAtualizado.getEndereco());
                    enderecoRepository.save(espectador.endereco);
                    return repository.save(espectador);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }

    @PutMapping("{id}/vincular")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vinculaEvento(@PathVariable Long id, @RequestBody @Valid Evento evento) {
        Evento e = eventoRepository.findById(evento.id).orElseThrow();
        repository.findById(id)
                .map(espectador -> {
                    espectador.eventos.add(e);
                    return repository.save(espectador);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }

}
