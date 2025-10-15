package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.model.perfil.Usuario;

public interface UsuarioService {
  Usuario findByEmailAndSenha(String email, String senha);
  Usuario create(Usuario usuario);
  void update(Usuario usuario);
  void delete(Long id);
  Usuario findById(Long id);
  void alterarSenha(String email, String novaSenha);
  void resetarSenha(Long id);
  public Long count();
  public Long count(String nome);
}

