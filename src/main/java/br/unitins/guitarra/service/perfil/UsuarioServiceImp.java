package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.validation.ValidationException;
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
      throw ValidationException.of("email","Usuário ou senha inválidos");
    return usuario;
  }

  @Override
  public Usuario create(Usuario request) {
    validarEmailUsuario(request);

    Usuario newUsuario = new Usuario();
    newUsuario.setEmail(request.getEmail());
    newUsuario.setSenha(request.getSenha());

    usuarioRepository.persist(newUsuario);
    return newUsuario;
  }

  @Override
  public void update(Usuario request) {
    Usuario usuario = usuarioRepository.findById(request.getId());
    if (usuario == null)
      throw new RuntimeException("Usuário não encontrado");
    
    usuario.setEmail(usuario.getEmail());
    usuario.setSenha(usuario.getSenha());

    usuarioRepository.persist(usuario);
  }

  @Override
  public void delete(Long id) {
    usuarioRepository.deleteById(id);
  }

  @Override
  public Usuario findById(Long id) {
    return usuarioRepository.findById(id);
  }

  @Override
  public void alterarSenha(Usuario request, String novaSenha) {
    Usuario usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), request.getSenha());
    if (usuario == null)
      throw ValidationException.of("senha", "usuario não encontrado ou senha inválida");

    usuario.setSenha(novaSenha);
    usuarioRepository.persist(usuario);
  }

  @Override
  public void resetarSenha(Long id) {
    Usuario usuario = usuarioRepository.findById(id);
    if (usuario == null)
      throw ValidationException.of("id", "Usuário não encontrado");

    usuario.setSenha("123456");
    usuarioRepository.persist(usuario);
  }

  @Override
  public Long count() {
    return usuarioRepository.count();
  }

  @Override
  public Long count(String nome) {
    return usuarioRepository.count("email", nome);
  }
  
  // Validação

  public void validarEmailUsuario(Usuario request) {
    Usuario usuarioExistente = usuarioRepository.findByEmail(request.getEmail());
    if(usuarioExistente == null)
      throw ValidationException.of("email","Já existe um usuário cadastrado com este e-mail.");
  }
}
