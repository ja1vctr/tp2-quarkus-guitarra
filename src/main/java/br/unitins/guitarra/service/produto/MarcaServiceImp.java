package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.MarcaRequest;
import br.unitins.guitarra.dto.produto.response.MarcaResponse;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;
import br.unitins.guitarra.model.produto.Marca;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.MarcaRepository;
import br.unitins.guitarra.repository.produto.ModeloRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class MarcaServiceImp implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public MarcaResponse create(MarcaRequest request) {
        if (repository.findByCnpj(request.cnpj()) != null) {
            throw ValidationException.of("cnpj", "O CNPJ '" + request.cnpj() + "' já existe.");
        }

        Marca newMarca = new Marca();
        newMarca.setNome(request.nome());
        if(request.cnpj().length() != 14)
            throw ValidationException.of("cnpj", "O CNPJ deve conter 14 caracteres.");
        newMarca.setCnpj(request.cnpj());          

        repository.persist(newMarca);

        return MarcaResponse.valueOf(newMarca);
    }

    @Override
    @Transactional
    public void update(Long id, MarcaRequest request) {
        Set<ConstraintViolation<MarcaRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Marca marca = repository.findById(id);
        if (marca == null) {
            throw ValidationException.of("id", "A marca com o id " + id + " não foi encontrada.");
        }
        Marca marcaComMesmoCnpj = repository.findByCnpj(request.cnpj());
        if (marcaComMesmoCnpj != null) {
            throw ValidationException.of("cnpj", "O CNPJ '" + request.cnpj() + "' já está em uso por outra marca.");
        }

        marca.setNome(request.nome());
        marca.setCnpj(request.cnpj());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw ValidationException.of("id", "A marca com o id " + id + " não foi encontrada.");
        }

        if (guitarraRepository.count("marca", marca) > 0) {
            throw ValidationException.of("marca", "Esta marca está em uso e não pode ser excluída.");
        }

        repository.delete(marca);
    }

    @Override
    public List<MarcaResponse> findAll(Integer page, Integer pageSize) {
        PanacheQuery<Marca> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(MarcaResponse::valueOf).
                      toList();
    }

    @Override
    public MarcaResponse findById(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) throw ValidationException.of("id", "A marca com o id " + id + " não foi encontrada.");
        return MarcaResponse.valueOf(marca);
    }

    @Override
    public List<MarcaResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(MarcaResponse::valueOf).toList();
    }
    
    @Override
    public List<ModeloResponse> findModelosByMarca(Long idMarca) {
        Marca marca = repository.findById(idMarca);

        if(marca == null) {
            throw ValidationException.of("idMarca", "A marca com o id " + idMarca + " não foi encontrada.");
        }

        return marca.getListaModelos()
            .stream()
            .map(ModeloResponse::valueOf)
            .toList();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByNome(String nome) {
        return repository.findByNome(nome).count();
    }

}
