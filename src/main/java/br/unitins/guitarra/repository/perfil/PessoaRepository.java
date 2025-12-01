package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class PessoaRepository implements PanacheRepository<Pessoa> {
  public Pessoa findByCpf(String cpf) {
    return find("cpf", cpf).firstResult();
  }
  public PanacheQuery<Pessoa> findByNome(String nome) {
    return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }

}
