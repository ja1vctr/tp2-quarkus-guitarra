package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.CorRequest;
import br.unitins.guitarra.dto.produto.response.CorResponse;
import jakarta.validation.Valid;

public interface CorService {
    CorResponse create(@Valid CorRequest request);
    void update(Long id, CorRequest request);
    void delete(Long id);
    List<CorResponse> findAll(int page, int pageSize);
    CorResponse findById(Long id);
    List<CorResponse> findByNome(String nome);
    List<CorResponse> findByCodigoHexadecimal(String codigoHexadecimal);
    long count();
    long countByNome(String nome);
    long countByCodigoHexadecimal(String codigoHexadecimal);
}
