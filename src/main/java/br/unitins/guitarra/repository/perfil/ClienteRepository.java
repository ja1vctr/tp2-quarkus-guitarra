package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

  public Cliente findByEmailAndSenha(String email, String senha) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByEmailAndSenha'");
  }
}
