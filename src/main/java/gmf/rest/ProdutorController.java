package gmf.rest;

import gmf.model.Evento;
import gmf.model.Produtor;
import gmf.repository.EnderecoRepository;
import gmf.repository.EventoRepository;
import gmf.repository.ProdutorRepository;
import gmf.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/produtor")
public class ProdutorController {

    private final ProdutorRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public ProdutorController(ProdutorRepository repository, UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping
    public List<Produtor> obterTodos() {
        return repository.findAll();
    }

    @GetMapping("usuario/{id}")
    public Produtor obterPorUsuario(@PathVariable Long id) {
        return repository.findByUsuario(usuarioRepository.findById(id).orElseThrow());
    }

    @GetMapping("{id}/eventos")
    public List<Evento> listaEventos(@PathVariable Long id) {
        return eventoRepository.findEventosByProdutorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produtor salvar(@RequestBody @Valid Produtor produtor) {
        enderecoRepository.save(produtor.endereco);
        return repository.save(produtor);
    }

    @GetMapping("{id}")
    public Produtor acharPorId(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produtor não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        repository
                .findById(id)
                .map(produtor -> {
                    repository.delete(produtor);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produtor não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id,
            @RequestBody @Valid Produtor produtorAtualizado) {
        repository
                .findById(id)
                .map(produtor -> {
                    produtor.setNome(produtorAtualizado.getNome());
                    produtor.setDataNascimento(produtorAtualizado.getDataNascimento());
                    produtor.setEndereco(produtorAtualizado.getEndereco());
                    enderecoRepository.save(produtor.endereco);
                    return repository.save(produtor);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produtor não encontrado"));
    }
}
