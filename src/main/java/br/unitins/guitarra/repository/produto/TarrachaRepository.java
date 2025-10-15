package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Tarracha;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TarrachaRepository implements PanacheRepository<Tarracha> {

    public PanacheQuery<Tarracha> findByMarca(String marca) {
        return find("UPPER(marca) LIKE ?1", "%" + marca.toUpperCase() + "%");
    }

    public PanacheQuery<Tarracha> findByModelo(String modelo) {
        return find("UPPER(modelo) LIKE ?1", "%" + modelo.toUpperCase() + "%");
    }

}