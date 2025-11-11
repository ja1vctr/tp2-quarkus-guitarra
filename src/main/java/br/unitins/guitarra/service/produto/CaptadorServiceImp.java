package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.CaptadorRequest;
import br.unitins.guitarra.dto.produto.response.CaptadorResponse;
import br.unitins.guitarra.model.produto.Captador;
import br.unitins.guitarra.model.produto.PosicaoCaptador;
import br.unitins.guitarra.repository.produto.CaptadorRepository;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;

@ApplicationScoped
public class CaptadorServiceImp implements CaptadorService {

    @Inject
    CaptadorRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public CaptadorResponse create(CaptadorRequest request) {
        

        Captador newCaptador = new Captador();
        newCaptador.setMarca(request.marca());
        newCaptador.setModelo(request.modelo());
        newCaptador.setPosicao(PosicaoCaptador.valueOf(request.posicao()));

        repository.persist(newCaptador);

        return CaptadorResponse.valueOf(newCaptador);
    }

    @Override
    @Transactional
    public void update(Long id, CaptadorRequest request) {

        Captador captador = repository.findById(id);
        if (captador == null) throw ValidationException.of("id", "O captador com o id " + id + " não foi encontrado.");

        captador.setMarca(request.marca());
        captador.setModelo(request.modelo());
        captador.setPosicao(PosicaoCaptador.valueOf(request.posicao()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Captador captador = repository.findById(id);

        if (captador == null) throw ValidationException.of("id", "O captador com o id " + id + " não foi encontrado.");

        long countBraco = guitarraRepository.count("captadorBraco", captador);
        long countMeio = guitarraRepository.count("captadorMeio", captador);
        long countPonte = guitarraRepository.count("captadorPonte", captador);

        if (countBraco > 0 || countMeio > 0 || countPonte > 0) {
            throw ValidationException.of("captador", "Este captador está em uso e não pode ser excluído.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<CaptadorResponse> findAll(int page, int pageSize) {
        return repository.findAll()
                .page(page, pageSize)
                .stream()
                .map(CaptadorResponse::valueOf)
                .toList();
    }

    @Override
    public CaptadorResponse findById(Long id) {
        Captador captador = repository.findById(id);
        if (captador == null) throw ValidationException.of("id", "O captador com o id " + id + " não foi encontrado.");
        return CaptadorResponse.valueOf(captador);
    }

    @Override
    public List<CaptadorResponse> findByModelo(String modelo) {
        return repository.findByModelo(modelo).stream().map(CaptadorResponse::valueOf).toList();
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
