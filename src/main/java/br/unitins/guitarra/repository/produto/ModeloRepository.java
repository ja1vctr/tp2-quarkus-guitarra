package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
  
  public PanacheQuery<Modelo> findByNome(String nome) {
    return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }
}
