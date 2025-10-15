package br.unitins.guitarra.repository.produto;

import br.unitins.guitarra.model.produto.Marca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    public PanacheQuery<Marca> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public Marca findByCnpj (String cnpj) {
        return find( "cnpj = ?1", cnpj).firstResult();
    }
}