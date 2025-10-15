package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Cor;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CorRepository implements PanacheRepository<Cor> {

    public PanacheQuery<Cor> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public PanacheQuery<Cor> findByCodigoHexadecimal(String codigoHexadecimal) {
        return find("UPPER(codigoHexadecimal) LIKE ?1", "%" + codigoHexadecimal.toUpperCase() + "%");
    }

    public Cor findCodigoHexadecimal(String codigoHexadecimal) {
        if (codigoHexadecimal == null) {
            return null;
        }
        return find("UPPER(codigoHexadecimal) = ?1", codigoHexadecimal.toUpperCase()).firstResult();
    }
}