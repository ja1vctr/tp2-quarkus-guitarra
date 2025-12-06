package br.unitins.guitarra.service.perfil;

import java.util.List;

import br.unitins.guitarra.dto.perfil.request.FuncionarioReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.FuncionarioRequest;
import br.unitins.guitarra.dto.perfil.response.FuncionarioResponse;
import br.unitins.guitarra.dto.perfil.response.UsuarioResponse;
import br.unitins.guitarra.model.perfil.Funcionario;

public interface FuncionarioService {
  FuncionarioResponse create(FuncionarioRequest request);
  void update(long id, FuncionarioReduzidoRequest request);
  void update(String email, FuncionarioReduzidoRequest request);
  void delete(Long id);
  void alterarSenha(String email, String novaSenha);
  void alterarEmail(String email, String novoEmail);
  void resetarSenha(Long id);
  List<FuncionarioResponse> findAll(Integer page, Integer pageSize);
  List<FuncionarioResponse> findAll();
  FuncionarioResponse findById(Long id);
  UsuarioResponse findEmailbyId(long id);
  List<FuncionarioResponse> findByNome(String nome);
  Funcionario findByEmailAndSenha(String email, String senha);
  FuncionarioResponse findByEmail(String email);
  public Long count();
  public Long count(String nome);
  Long findUsuarioIdByFuncionarioId(Long id);
  Long findUsuarioIdByEmail(String email);
}
