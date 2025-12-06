package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.service.hash.HashService;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImp implements UsuarioService {
  @Inject
  UsuarioRepository usuarioRepository;

  @Inject
  HashService hashService;

  @Override
  public Usuario findByEmailAndSenha(String email, String senha) {
    // Espera receber a senha já hash para consulta
    return usuarioRepository.findByEmailAndSenha(email, senha);
  }

  @Override
  public Usuario create(Usuario request) {
    validarEmailUsuario(request);

    Usuario newUsuario = new Usuario();
    newUsuario.setEmail(request.getEmail());
    newUsuario.setSenha(hashService.getHashSenha(request.getSenha()));

    usuarioRepository.persist(newUsuario);
    return newUsuario;
  }

  @Override
  public void update(Usuario request) {
    Usuario usuario = usuarioRepository.findById(request.getId());
    if (usuario == null) {
      throw ValidationException.of("id", "Usuario nao encontrado");
    }

    usuario.setEmail(request.getEmail());
    usuario.setSenha(hashService.getHashSenha(request.getSenha()));

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
    String senhaAtualHash = hashService.getHashSenha(request.getSenha());
    Usuario usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), senhaAtualHash);
    if (usuario == null) {
      throw ValidationException.of("senha", "Usuario nao encontrado ou senha invalida");
    }

    usuario.setSenha(hashService.getHashSenha(novaSenha));
    usuarioRepository.persist(usuario);
  }

  @Override
  public void resetarSenha(Long id) {
    Usuario usuario = usuarioRepository.findById(id);
    if (usuario == null) {
      throw ValidationException.of("id", "Usuario nao encontrado");
    }

    usuario.setSenha(hashService.getHashSenha("123456"));
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
  
  // Validacao

  public void validarEmailUsuario(Usuario request) {
    Usuario usuarioExistente = usuarioRepository.findByEmail(request.getEmail());
    if(usuarioExistente != null) {
      throw ValidationException.of("email","Ja existe um usuario cadastrado com este e-mail.");
    }
  }
}
