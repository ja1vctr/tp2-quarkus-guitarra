package br.unitins.guitarra.service.perfil;

import java.util.List;

import br.unitins.guitarra.dto.perfil.request.ClienteReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.ClienteRequest;
import br.unitins.guitarra.dto.perfil.response.ClienteResponse;
import br.unitins.guitarra.dto.perfil.response.UsuarioResponse;
import br.unitins.guitarra.model.perfil.Cliente;

public interface ClienteService {
  ClienteResponse create(ClienteRequest request);
  void update(long id, ClienteReduzidoRequest request);
  void delete(Long id);
  List<ClienteResponse> findAll(Integer page, Integer pageSize);
  List<ClienteResponse> findAll();
  Cliente findByEmailAndSenha(String email, String senha);
  ClienteResponse findById(Long id);
  UsuarioResponse findEmailbyId(long id);
  List<ClienteResponse> findByNome(String nome);
  ClienteResponse findByEmail(String email);
  void alterarSenha(String email, String novaSenha);
  void alterarEmail(String email, String novoEmail);
  void resetarSenha(Long id);
  public Long count();
  public Long count(String nome);
}
