package gmf.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Espectador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String dataNascimento;
    @OneToOne(cascade = CascadeType.ALL)
    public Endereco endereco;
    @ManyToMany(mappedBy = "espectadores")
    public List<Evento> eventos;
    public String papel = "USER";
    @OneToOne(cascade = CascadeType.DETACH)
    public Usuario usuario;
}
