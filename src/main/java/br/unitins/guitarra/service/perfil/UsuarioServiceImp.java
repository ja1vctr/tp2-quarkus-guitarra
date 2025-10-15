package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImp implements UsuarioService {
  @Inject
  UsuarioRepository usuarioRepository;

  @Override
  public Usuario findByEmailAndSenha(String email, String senha) {
    Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
    if (usuario == null)
      throw new RuntimeException("Usuário ou senha inválidos");
    return usuario;
    
  }

  @Override
  public Usuario create(Usuario usuario) {
    Usuario newUsuario = new Usuario();
    newUsuario.setEmail(usuario.getEmail());
    newUsuario.setSenha(usuario.getSenha());

    usuarioRepository.persist(newUsuario);
    return newUsuario;
  }

  @Override
  public void update(Usuario usuario) {
    // TODO Auto-generated method saaatub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public Usuario findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public void alterarSenha(String email, String novaSenha) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'alterarSenha'");
  }

  @Override
  public void resetarSenha(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resetarSenha'");
  }

  @Override
  public Long count() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

  @Override
  public Long count(String nome) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }
  
}
