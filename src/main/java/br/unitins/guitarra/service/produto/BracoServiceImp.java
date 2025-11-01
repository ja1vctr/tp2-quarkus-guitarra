package br.unitins.guitarra.service.produto;

import java.util.Date;
import java.util.List;

import br.unitins.guitarra.dto.produto.request.BracoRequest;
import br.unitins.guitarra.dto.produto.response.BracoResponse;
import br.unitins.guitarra.model.produto.Braco;
import br.unitins.guitarra.repository.produto.BracoRepository;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class BracoServiceImp implements BracoService {

    @Inject
    BracoRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public BracoResponse create(BracoRequest request) {

        Braco newBraco = new Braco();
        newBraco.setFormato(request.formato());
        newBraco.setMadeira(request.madeira());
        newBraco.setNumeroDeTrastes(request.numeroDeTrastes());
        newBraco.setDataDeFabricacao(request.dataDeFabricacao());
        newBraco.setDescricao(request.descricao());

        repository.persist(newBraco);

        return BracoResponse.valueOf(newBraco);
    }

    @Override
    @Transactional
    public void update(Long id, BracoRequest request) {
        Braco braco = repository.findById(id);
        if (braco == null) throw new NotFoundException("Braço não encontrado pelo ID " + id);

        braco.setFormato(request.formato());
        braco.setMadeira(request.madeira());
        braco.setNumeroDeTrastes(request.numeroDeTrastes());
        braco.setDataDeFabricacao(request.dataDeFabricacao());
        braco.setDescricao(request.descricao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id) == null) {
            throw ValidationException.of("id", "O braço com o id " + id + " não foi encontrado.");
        }

        Braco braco = repository.findById(id);

        if (guitarraRepository.count("braco", braco) > 0) {
            throw ValidationException.of("braco", "Este braço está em uso e não pode ser excluído.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<BracoResponse> findAll(Integer page, Integer pageSize) {
        PanacheQuery<Braco> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(BracoResponse::valueOf).
                      toList();
    }

    @Override
    public BracoResponse findById(Long id) {
        Braco braco = repository.findById(id);
        if (braco == null) throw new NotFoundException("Braço não encontrado pelo ID " + id);
        return BracoResponse.valueOf(braco);
    }

    @Override
    public List<BracoResponse> findByFormato(String formato) {
        return repository.findByFormato(formato).stream().map(BracoResponse::valueOf).toList();
    }
    
    @Override
    public List<BracoResponse> findByMadeira(String madeira) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByMadeira'");
    }

    @Override
    public List<BracoResponse> findByAno(Date dataDeFabricacao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAno'");
    }
    
    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long count(String formato) {
        return repository.count("UPPER(formato) LIKE ?1", "%" + formato.toUpperCase() + "%");
    }


    //-----------------------------------------------------------------------------------------------------------------------

}
