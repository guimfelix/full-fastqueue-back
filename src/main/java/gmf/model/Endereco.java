package gmf.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nomeRua;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;
    public String estado;
    public String pais;
    public String cep;
}
