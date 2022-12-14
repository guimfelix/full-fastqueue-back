package gmf.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Papel> roles = new HashSet<>();

  private Boolean isCadastrado = false;

  public Usuario() {
  }

  public Usuario(String username, String email, String password, Boolean isCadastrado) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isCadastrado = isCadastrado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Papel> getRoles() {
    return roles;
  }

  public void setRoles(Set<Papel> roles) {
    this.roles = roles;
  }

  public void setIsCadastrado(Boolean isCadastrado) {
    this.isCadastrado = isCadastrado;
  }

  public Boolean getIsCadastrado() {
    return isCadastrado;
  }
}
