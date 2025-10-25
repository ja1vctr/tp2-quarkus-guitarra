package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.MarcaRequest;
import br.unitins.guitarra.dto.produto.response.MarcaResponse;
import jakarta.validation.Valid;

public interface MarcaService {
    MarcaResponse create(@Valid MarcaRequest request);
    void update(Long id, MarcaRequest request);
    void delete(Long id);
    List<MarcaResponse> findAll(Integer page, Integer pageSize);
    MarcaResponse findById(Long id);
    List<MarcaResponse> findByNome(String nome);
    long count();
    long countByNome(String nome);
}
