package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Ponte;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PonteRepository implements PanacheRepository<Ponte> {

    public PanacheQuery<Ponte> findByMarca(String marca) {
        return find("UPPER(marca) LIKE ?1", "%" + marca.toUpperCase() + "%");
    }

    public PanacheQuery<Ponte> findByModelo(String modelo) {
        return find("UPPER(modelo) LIKE ?1", "%" + modelo.toUpperCase() + "%");
    }

}