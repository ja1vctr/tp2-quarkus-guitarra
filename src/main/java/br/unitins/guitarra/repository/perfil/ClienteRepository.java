package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
  public PanacheQuery<Cliente> findByNome(String nome) {
    return find("UPPER(pessoa.nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }

  public Cliente findByEmail(String email) {      
    String query = "SELECT c FROM Cliente c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.role = 'CLIENTE'";
    return find(query, email).firstResult();
  }
    
  public PanacheQuery<Cliente> findByEmailAndSenha(String email, String senha) {
    String query = "SELECT c FROM Cliente c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.senha = ?2 AND u.role = 'CLIENTE'";
    return find(query, email, senha); 
  }
}
