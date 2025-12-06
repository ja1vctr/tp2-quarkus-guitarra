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
  void update(String email, ClienteReduzidoRequest request);
  void delete(Long id);
  void alterarSenha(String email, String novaSenha);
  void alterarEmail(String email, String novoEmail);
  void resetarSenha(Long id);
  List<ClienteResponse> findAll(Integer page, Integer pageSize);
  List<ClienteResponse> findAll();
  ClienteResponse findById(Long id);
  UsuarioResponse findEmailbyId(long id);
  List<ClienteResponse> findByNome(String nome);
  Cliente findByEmailAndSenha(String email, String senha);
  ClienteResponse findByEmail(String email);
  public Long count();
  public Long count(String nome);
  Long findUsuarioIdByClienteId(Long id);
  Long findUsuarioIdByEmail(String email);
}
