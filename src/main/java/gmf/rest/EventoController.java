package gmf.rest;

import gmf.model.Evento;
import gmf.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoRepository repository;

    @Autowired
    public EventoController(EventoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Evento> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento salvar(@RequestBody @Valid Evento evento) {
        return repository.save(evento);
    }

    @GetMapping("{id}")
    public Evento acharPorId(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(evento -> {
                    repository.delete(evento);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id,
            @RequestBody @Valid Evento eventoAtualizado) {
        repository
                .findById(id)
                .map(evento -> {
                    evento.setDataEvento(eventoAtualizado.getDataEvento());
                    evento.setEndereco(eventoAtualizado.getEndereco());
                    evento.setHorarioEvento(eventoAtualizado.getHorarioEvento());
                    evento.setNomeEvento(eventoAtualizado.getNomeEvento());
                    evento.setNomeLocalEvento(eventoAtualizado.getNomeLocalEvento());
                    evento.setQuantidadeEspectadoresEsperada(eventoAtualizado.getQuantidadeEspectadoresEsperada());
                    return repository.save(evento);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }
}
