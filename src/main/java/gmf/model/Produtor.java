package gmf.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Produtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String dataNascimento;
    @OneToOne(cascade = CascadeType.ALL)
    public Endereco endereco;
    @OneToMany(mappedBy = "produtor", targetEntity = Evento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Evento> eventos;
    public String papel = "GERENTE";
    @OneToOne(cascade = CascadeType.DETACH)
    public Usuario usuario;

}
