package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {
  public PanacheQuery<Funcionario> findByNome(String nome) {
    return find("UPPER(pessoa.nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }

  public Funcionario findByEmail(String email) {      
    String query = "SELECT c FROM Funcionario c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.role = 'FUNCIONARIO'";
    return find(query, email).firstResult();
  }
    
  public PanacheQuery<Funcionario> findByEmailAndSenha(String email, String senha) {
    String query = "SELECT c FROM Funcionario c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.senha = ?2 AND u.role = 'FUNCIONARIO'";
    return find(query, email, senha); 
}   
}
