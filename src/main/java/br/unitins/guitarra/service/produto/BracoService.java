package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.BracoRequest;
import br.unitins.guitarra.dto.produto.response.BracoResponse;

public interface BracoService {
  BracoResponse create(BracoRequest request);
  void update(Long id, BracoRequest request);
  void delete(Long id);
  List<BracoResponse> findAll(Integer page, Integer pageSize);
  BracoResponse findById(Long id);
  List<BracoResponse> findByFormato(String formato);
  Long count();
  Long count(String formato);  
}
