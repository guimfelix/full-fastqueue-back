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
    @ManyToOne
    public Endereco endereco;
    @OneToMany
    public List<Evento> eventos;
    public String papel = "GERENTE";
}
