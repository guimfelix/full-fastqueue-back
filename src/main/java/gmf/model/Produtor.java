package gmf.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Produtor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String dataNascimento;

    @OneToOne
    public Endereco endereco;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "produtor_id")
    public List<Evento> eventos;
    public String papel = "GERENTE";

    @OneToOne(cascade = CascadeType.DETACH)
    public Usuario usuario;

}
