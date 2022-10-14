package gmf.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Espectador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String dataNascimento;
    @OneToOne
    public Endereco endereco;
    @JsonIgnore
    @ManyToMany
    public List<Evento> eventos;
    public String papel = "USER";
    @OneToOne(cascade = CascadeType.DETACH)
    public Usuario usuario;
}
