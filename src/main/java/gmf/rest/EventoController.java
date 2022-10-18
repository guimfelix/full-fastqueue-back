package gmf.rest;

import gmf.model.Evento;
import gmf.payload.response.MessageResponse;
import gmf.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    // fazer no service do evento de front tb
    @GetMapping("/busca")
    public ResponseEntity<?> buscaEvento(@Valid @RequestBody String nomeEvento) {
        List<Evento> lista = repository.findByNomeEvento("%" + nomeEvento + "%");
        // System.out.println(">>>> lista" + lista);
        if (lista.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Não foi encontrado resultados com esse nome"));
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    public Evento acharPorId(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
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
    public void atualizar(@PathVariable Long id,
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
