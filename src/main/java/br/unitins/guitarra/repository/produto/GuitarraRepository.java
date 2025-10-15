package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Guitarra;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraRepository implements PanacheRepository<Guitarra> {
    public PanacheQuery<Guitarra> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public PanacheQuery<Guitarra> findByModelo (String modelo) {
        return find("UPPER(modelo) LIKE ?1", "%" + modelo.toUpperCase() + "%");
    }

}
