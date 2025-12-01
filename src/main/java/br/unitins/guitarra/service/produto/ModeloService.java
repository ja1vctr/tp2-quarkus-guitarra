package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.ModeloRequest;
import br.unitins.guitarra.dto.produto.response.MarcaResponse;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;
import jakarta.validation.Valid;

public interface ModeloService {
  ModeloResponse create(@Valid ModeloRequest request);
  void update(Long id, ModeloRequest request);
  void delete(Long id);
  List<ModeloResponse> findAll(Integer page, Integer pageSize);
  ModeloResponse findById(Long id);
  List<ModeloResponse> findByNome(String nome);
  List<MarcaResponse> findMarcasByModelo(Long idModelo);
  List<MarcaResponse> addListMarcasByModelo(Long idModelo);
  long count();
  long countByNome(String nome);
}
