package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.dto.perfil.request.ClienteRequest;
import br.unitins.guitarra.dto.perfil.response.ClienteResponse;
import br.unitins.guitarra.model.perfil.Cliente;

public interface ClienteService {
  Cliente findByEmailAndSenha(String email, String senha);
  Cliente create(ClienteRequest request);
  void update(ClienteRequest request);
  void delete(Long id);
  ClienteResponse findById(Long id);
  void alterarSenha(ClienteRequest request, String novaSenha);
  void resetarSenha(Long id);
  public Long count();
  public Long count(String nome);
}
