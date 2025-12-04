package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {
  public PanacheQuery<Funcionario> findByCargo(String cargo) {
    return find("UPPER(cargo) LIKE ?1", "%" + cargo.toUpperCase()+ "%");
  }

  public PanacheQuery<Funcionario> findByEmailAndSenha(String email, String senha) {
    return find("u.email = ?1 AND u.senha = ?2", email, senha); 
  } 

  public PanacheQuery<Funcionario> findByNome(String nome) {
    return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }
}
