package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.CaptadorRequest;
import br.unitins.guitarra.dto.produto.response.CaptadorResponse;
import jakarta.validation.Valid;

public interface CaptadorService {
    CaptadorResponse create(@Valid CaptadorRequest request);
    void update(Long id, CaptadorRequest request);
    void delete(Long id);
    List<CaptadorResponse> findAll(int page, int pageSize);
    CaptadorResponse findById(Long id);
    List<CaptadorResponse> findByModelo(String modelo);
    long count();
    long countByModelo(String modelo);
}
