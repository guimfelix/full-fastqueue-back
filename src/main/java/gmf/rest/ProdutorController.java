package gmf.rest;

import gmf.model.Produtor;
import gmf.repository.ProdutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtor")
public class ProdutorController {

    private final ProdutorRepository repository;

    @Autowired
    public ProdutorController(ProdutorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produtor> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produtor salvar(@RequestBody @Valid Produtor produtor) {
        return repository.save(produtor);
    }

    @GetMapping("{id}")
    public Produtor acharPorId(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produtor não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
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
    public void atualizar(@PathVariable Integer id,
            @RequestBody @Valid Produtor produtorAtualizado) {
        repository
                .findById(id)
                .map(produtor -> {
                    produtor.setNome(produtorAtualizado.getNome());
                    produtor.setDataNascimento(produtorAtualizado.getDataNascimento());
                    produtor.setEndereco(produtorAtualizado.getEndereco());
                    return repository.save(produtor);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produtor não encontrado"));
    }
}
