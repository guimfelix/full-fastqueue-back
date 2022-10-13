package gmf.rest;

import org.hibernate.event.spi.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import gmf.model.Espectador;
import gmf.model.Usuario;
import gmf.repository.EspectadorRepository;
import gmf.repository.UsuarioRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/espectador")
public class EspectadorController {

    @Autowired
    UsuarioRepository userRepository;

    private final EspectadorRepository repository;

    @Autowired
    public EspectadorController(EspectadorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @GetMapping
    public List<Espectador> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Espectador salvar(@RequestBody @Valid Espectador espectador) {
        return repository.save(espectador);
    }

    @Transactional
    @GetMapping("{id}")
    public Espectador acharPorId(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }

    @Transactional
    @GetMapping("usuario/{id}")
    public Espectador acharIdUsuarioPorId(@PathVariable Long id) {
        return repository
                .findByUsuario(id);
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
                    return repository.save(espectador);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }
}
