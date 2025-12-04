package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

  @Inject
  PessoaRepository pessoaRepository;


  public PanacheQuery<Cliente> findByNome(String nome) {
    return find("UPPER(pessoa.nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }

  public Cliente findByEmail(String email) {
      // HQL:
      // 1. SELECT c FROM Cliente c: Seleciona a entidade Cliente (alias c).
      // 2. JOIN c.pessoa p: Faz JOIN com a Pessoa associada (alias p).
      // 3. JOIN p.usuarios u: Faz JOIN com a coleção de Usuarios da Pessoa (alias u).
      // 4. WHERE u.email = ?1: Filtra o resultado onde o email do Usuario (u) coincide com o parâmetro.
      
      String query = "SELECT c FROM Cliente c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.role = 'Cliente'";
      
      return find(query, email).firstResult();
  }
    
    // CORRIGINDO O findByEmailAndSenha TAMBÉM (pois ele tem o mesmo problema de acesso à coleção)
  public PanacheQuery<Cliente> findByEmailAndSenha(String email, String senha) {
      String query = "SELECT c FROM Cliente c JOIN c.pessoa p JOIN p.usuarios u WHERE u.email = ?1 AND u.senha = ?2 AND u.role = 'Cliente'";
      
      // PanacheQuery retorna a lista, então você deve chamar firstResult() no service ou aqui:
      return find(query, email, senha); 
  }
}
