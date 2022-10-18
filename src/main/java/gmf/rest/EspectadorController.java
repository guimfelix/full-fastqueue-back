package gmf.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import gmf.dao.EspectadorDao;
import gmf.model.Espectador;
import gmf.repository.EspectadorRepository;

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

    public EspectadorController(EspectadorDao espectadorDao, EspectadorRepository repository) {
        this.repository = repository;
        this.espectadorDao = espectadorDao;
    }

    @GetMapping
    public List<Espectador> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Espectador salvar(@RequestBody @Valid Espectador espectador) {
        return espectadorDao.salvarEspectador(espectador);
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
                    return repository.save(espectador);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espectador não encontrado"));
    }
}
