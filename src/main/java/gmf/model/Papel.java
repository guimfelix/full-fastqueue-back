package gmf.model;

import javax.persistence.*;

@Entity
@Table(name = "papel")
public class Papel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private EPapel name;

  public Papel() {

  }

  public Papel(EPapel name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EPapel getName() {
    return name;
  }

  public void setName(EPapel name) {
    this.name = name;
  }
}