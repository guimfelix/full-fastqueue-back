package gmf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nomeEvento;
    public String dataEvento;
    public String horarioEvento;
    public String nomeLocalEvento;
    public Integer quantidadeEspectadoresEsperada;
    @OneToOne(cascade = CascadeType.ALL)
    public Endereco endereco;
    @JsonIgnore
    @ManyToOne
    public Produtor produtor;
    @JsonIgnore
    @ManyToMany

    public List<Espectador> espectadores = new ArrayList<>();
}
