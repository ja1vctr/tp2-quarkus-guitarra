package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.TarrachaRequest;
import br.unitins.guitarra.dto.produto.response.TarrachaResponse;
import jakarta.validation.Valid;

public interface TarrachaService {
    TarrachaResponse create(@Valid TarrachaRequest request);
    void update(Long id, TarrachaRequest request);
    void delete(Long id);
    List<TarrachaResponse> findAll(Integer page, Integer pageSize);
    TarrachaResponse findById(Long id);
    List<TarrachaResponse> findByModelo(String modelo);
    long count();
    long countByModelo(String modelo);
}