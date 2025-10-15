package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Braco;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BracoRepository implements PanacheRepository<Braco> {
  public PanacheQuery<Braco> findByFormato(String formato) {
        return find("UPPER(formato) LIKE ?1", "%" + formato.toUpperCase() + "%");
    }
}