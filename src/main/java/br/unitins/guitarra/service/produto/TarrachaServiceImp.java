package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.TarrachaRequest;
import br.unitins.guitarra.dto.produto.response.TarrachaResponse;
import br.unitins.guitarra.model.produto.Tarracha;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.TarrachaRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class TarrachaServiceImp implements TarrachaService {

    @Inject
    TarrachaRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public TarrachaResponse create(TarrachaRequest request) {
        Set<ConstraintViolation<TarrachaRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Tarracha newTarracha = new Tarracha();
        newTarracha.setMarca(request.marca());
        newTarracha.setMaterial(request.material());
        newTarracha.setModelo(request.modelo());

        repository.persist(newTarracha);

        return TarrachaResponse.valueOf(newTarracha);
    }

    @Override
    @Transactional
    public void update(Long id, TarrachaRequest request) {
        Set<ConstraintViolation<TarrachaRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Tarracha tarracha = repository.findById(id);
        if (tarracha == null) {
            throw ValidationException.of("id", "A tarracha com o id " + id + " não foi encontrada.");
        }

        tarracha.setMarca(request.marca());
        tarracha.setMaterial(request.material());
        tarracha.setModelo(request.modelo());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tarracha tarracha = repository.findById(id);
        if (tarracha == null) {
            throw ValidationException.of("id", "A tarracha com o id " + id + " não foi encontrada.");
        }

        if (guitarraRepository.count("tarracha", tarracha) > 0) {
            throw ValidationException.of("tarracha", "Esta tarracha está em uso e não pode ser excluída.");
        }

        repository.delete(tarracha);
    }

    @Override
    public List<TarrachaResponse> findAll(Integer page, Integer pageSize) {
        PanacheQuery<Tarracha> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(TarrachaResponse::valueOf).
                      toList();
    }

    @Override
    public TarrachaResponse findById(Long id) {
        Tarracha tarracha = repository.findById(id);
        if (tarracha == null) throw ValidationException.of("id", "A tarracha com o id " + id + " não foi encontrada.");
        return TarrachaResponse.valueOf(tarracha);
    }

    @Override
    public List<TarrachaResponse> findByModelo(String modelo) {
        return repository.findByModelo(modelo).stream().map(TarrachaResponse::valueOf).toList();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByModelo(String modelo) {
        return repository.findByModelo(modelo).count();
    }
}