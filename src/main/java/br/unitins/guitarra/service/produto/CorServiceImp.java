package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.CorRequest;
import br.unitins.guitarra.dto.produto.response.CorResponse;
import br.unitins.guitarra.model.produto.Cor;
import br.unitins.guitarra.repository.produto.CorRepository;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class CorServiceImp implements CorService {

    @Inject
    CorRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public CorResponse create(CorRequest request) {
        validarNome(request.nome());
        validarCodigoExadecimal(request.codigoHexadecimal());

        Cor newCor = new Cor();
        newCor.setNome(request.nome());        
        newCor.setCodigoHexadecimal(request.codigoHexadecimal().toUpperCase());

        repository.persist(newCor);

        return CorResponse.valueOf(newCor);
    }

    @Override
    @Transactional
    public void update(Long id, CorRequest request) {
        Set<ConstraintViolation<CorRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Cor cor = repository.findById(id);
        if (cor == null) {
            throw ValidationException.of("id", "A cor com o id " + id + " não foi encontrada.");
        }

        cor.setNome(request.nome());
        cor.setCodigoHexadecimal(request.codigoHexadecimal().toUpperCase());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cor cor = repository.findById(id);
        if (cor == null) {
            throw ValidationException.of("id", "A cor com o id " + id + " não foi encontrada.");
        }

        if (guitarraRepository.count("cor", cor) > 0) {
            throw ValidationException.of("cor", "Esta cor está em uso e não pode ser excluída.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<CorResponse> findAll(int page, int pageSize) {
        return repository.findAll().page(page, pageSize).stream().map(CorResponse::valueOf).toList();
    }

    @Override
    public CorResponse findById(Long id) {
        Cor cor = repository.findById(id);
       if (cor == null) {
            throw ValidationException.of("id", "A cor com o id " + id + " não foi encontrada.");
        }
        return CorResponse.valueOf(cor);
    }

    @Override
    public List<CorResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(CorResponse::valueOf).toList();
    }

    @Override
    public List<CorResponse> findByCodigoHexadecimal(String codigoHexadecimal) {
        return repository.findByCodigoHexadecimal(codigoHexadecimal).stream().map(CorResponse::valueOf).toList();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByNome(String nome) {
        return repository.findByNome(nome).count();
    }

    @Override
    public long countByCodigoHexadecimal(String codigoHexadecimal) {
        return repository.findByCodigoHexadecimal(codigoHexadecimal).count();
    }

    //--------------------------------------------------------------------------------------------------------------------------

    public void validarNome(String nome) {
        PanacheQuery<Cor> query = repository.findByNome(nome);
        if (query.firstResult() != null)
            throw ValidationException.of("nome", "O nome " + nome + " já existe.");
    }
    
    public void validarCodigoExadecimal(String codigoHexadecimal) {
        PanacheQuery<Cor> query = repository.findByCodigoHexadecimal(codigoHexadecimal);
        if (query.firstResult() != null)
            throw ValidationException.of("codigoHexadecimal", "O codigoHexadecimal " + codigoHexadecimal + " já existe.");
    }
}
