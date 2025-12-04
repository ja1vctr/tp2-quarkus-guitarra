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
  void delete(Long id);
  List<FuncionarioResponse> findAll(Integer page, Integer pageSize);
  List<FuncionarioResponse> findAll();
  Funcionario findByEmailAndSenha(String email, String senha);
  FuncionarioResponse findById(Long id);
  UsuarioResponse findEmailbyId(long id);
  List<FuncionarioResponse> findByNome(String nome);
  void alterarSenha(FuncionarioRequest request, String novaSenha);
  void alterarEmail(FuncionarioRequest request, String novoEmail);
  void resetarSenha(Long id);
  public Long count();
  public Long count(String nome);
}
