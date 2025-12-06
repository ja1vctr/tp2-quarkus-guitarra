package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.GuitarraRequest;
import br.unitins.guitarra.dto.produto.response.GuitarraResponse;
import jakarta.validation.Valid;

public interface GuitarraService {
  GuitarraResponse create(@Valid GuitarraRequest request);
  void update(GuitarraRequest request, Long id);
  void delete(Long id);
  List<GuitarraResponse> findAll(Integer page, Integer pageSize);
  GuitarraResponse findById(Long id);
  List<GuitarraResponse> findByNome(String nome);
  List<GuitarraResponse> findByModelo(String modelo);
  public Long count();
  public Long count(String nome);
  GuitarraResponse updateStatus(Long id, Boolean status);
}
