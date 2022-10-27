package gmf.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "details")
public class Produtor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String dataNascimento;

    @OneToOne(cascade = CascadeType.DETACH)
    public Endereco endereco;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "produtor_id")
    public List<Evento> eventos = new ArrayList<>();
    public String papel = "GERENTE";

    @OneToOne(cascade = CascadeType.DETACH)
    public Usuario usuario;

}
