package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.PonteRequest;
import br.unitins.guitarra.dto.produto.response.PonteResponse;
import jakarta.validation.Valid;

public interface PonteService {
    PonteResponse create(@Valid PonteRequest request);
    void update(Long id, PonteRequest request);
    void delete(Long id);
    List<PonteResponse> findAll(Integer page, Integer pageSize);
    PonteResponse findById(Long id);
    List<PonteResponse> findByModelo(String modelo);
    long count();
}