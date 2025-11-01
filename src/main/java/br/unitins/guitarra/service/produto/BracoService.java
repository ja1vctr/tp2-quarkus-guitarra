package br.unitins.guitarra.service.produto;

import java.util.Date;
import java.util.List;

import br.unitins.guitarra.dto.produto.request.BracoRequest;
import br.unitins.guitarra.dto.produto.response.BracoResponse;
import jakarta.validation.Valid;

public interface BracoService {
  BracoResponse create(@Valid BracoRequest request);
  void update(Long id, BracoRequest request);
  void delete(Long id);
  List<BracoResponse> findAll(Integer page, Integer pageSize);
  BracoResponse findById(Long id);
  List<BracoResponse> findByFormato(String formato);
  List<BracoResponse> findByMadeira(String madeira);
  List<BracoResponse> findByAno(Date dataDeFabricacao);
  Long count();
  Long count(String formato);  
}
