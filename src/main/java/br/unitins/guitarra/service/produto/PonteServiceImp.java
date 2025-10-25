package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.PonteRequest;
import br.unitins.guitarra.dto.produto.response.PonteResponse;
import br.unitins.guitarra.model.produto.Ponte;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.PonteRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class PonteServiceImp implements PonteService {

    @Inject
    PonteRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public PonteResponse create(PonteRequest request) {
        Set<ConstraintViolation<PonteRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Ponte newPonte = new Ponte();
        newPonte.setMarca(request.marca());
        newPonte.setModelo(request.modelo());

        repository.persist(newPonte);

        return PonteResponse.valueOf(newPonte);
    }

    @Override
    @Transactional
    public void update(Long id, PonteRequest request) {
        Set<ConstraintViolation<PonteRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Ponte ponte = repository.findById(id);
        if (ponte == null) {
            throw ValidationException.of("ponte", "Ponte não encontrada pelo ID " + id);
        }

        ponte.setMarca(request.marca());
        ponte.setModelo(request.modelo());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Ponte ponte = repository.findById(id);
        if (ponte == null) {
            throw ValidationException.of("ponte", "Ponte não encontrada pelo ID " + id);
        }

        if (guitarraRepository.count("ponte", ponte) > 0) {
            throw ValidationException.of("ponte", "Esta ponte está em uso e não pode ser excluída.");
        }

        repository.delete(ponte);
    }

    @Override
    public List<PonteResponse> findAll(Integer page, Integer pageSize) {
        PanacheQuery<Ponte> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(PonteResponse::valueOf).
                      toList();
    }

    @Override
    public PonteResponse findById(Long id) {
        Ponte ponte = repository.findById(id);
        if (ponte == null) throw ValidationException.of("ponte", "Ponte não encontrada pelo ID " + id);
        return PonteResponse.valueOf(ponte);
    }

    @Override
    public List<PonteResponse> findByModelo(String modelo) {
        return repository.findByModelo(modelo).stream().map(PonteResponse::valueOf).toList();
    }

    @Override
    public long count() {
        return repository.count();
    }  
}