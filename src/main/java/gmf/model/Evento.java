package gmf.model;

import java.io.Serializable;
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
    @OneToOne
    public Endereco endereco;
    @JsonIgnore
    @ManyToOne
    public Produtor produtor;
    @JsonIgnore
    @ManyToMany
    // @ToString.Exclude
    public List<Espectador> espectadores;
}
