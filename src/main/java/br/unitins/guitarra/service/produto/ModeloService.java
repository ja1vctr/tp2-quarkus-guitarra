package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.ModeloRequest;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;

public interface ModeloService {
  ModeloResponse create(ModeloRequest request);
  void update(Long id, ModeloRequest request);
  void delete(Long id);
  List<ModeloResponse> findAll(Integer page, Integer pageSize);
  ModeloResponse findById(Long id);
  List<ModeloResponse> findByNome(String nome);
  long count();
  long countByNome(String nome);
}
