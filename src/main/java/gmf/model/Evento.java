package gmf.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Evento {
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
    @ManyToOne
    public Produtor produtor;
    @ManyToMany
    public List<Espectador> espectadores;
}
