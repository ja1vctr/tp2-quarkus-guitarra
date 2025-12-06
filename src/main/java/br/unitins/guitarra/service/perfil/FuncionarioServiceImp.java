package br.unitins.guitarra.service.perfil;

import java.util.List;

import br.unitins.guitarra.dto.perfil.request.FuncionarioReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.FuncionarioRequest;
import br.unitins.guitarra.dto.perfil.response.FuncionarioResponse;
import br.unitins.guitarra.dto.perfil.response.UsuarioResponse;
import br.unitins.guitarra.model.perfil.Funcionario;
import br.unitins.guitarra.model.perfil.Pessoa;
import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.FuncionarioRepository;
import br.unitins.guitarra.repository.perfil.PessoaRepository;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.service.hash.HashService;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FuncionarioServiceImp implements FuncionarioService {
  @Inject
  FuncionarioRepository repository;

  @Inject
  PessoaRepository pessoaRepository;

  @Inject
  UsuarioRepository usuarioRepository;

  @Inject
  HashService hashService;

  @Override
  public Funcionario findByEmailAndSenha(String email, String senha) {
    return repository.findByEmailAndSenha(email, senha).firstResult();
  }

  @Override
  @Transactional
  public FuncionarioResponse create(FuncionarioRequest request) {
    if (request == null) {
      throw ValidationException.of("request", "Dados do funcionario sao obrigatorios.");
    }
    if (usuarioRepository.findByEmail(request.email()) != null) {
      throw ValidationException.of("email", "Ja existe um usuario com este e-mail.");
    }

    Funcionario funcionario = new Funcionario();
    funcionario.setCargo(request.cargo());
    funcionario.setDataAdmissao(request.dataAdmissao());
    funcionario.setSalario(request.salario());

    Pessoa pessoa = new Pessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
    pessoaRepository.persist(pessoa);
    funcionario.setPessoa(pessoa);

    Usuario usuario = new Usuario();
    usuario.setEmail(request.email());
    usuario.setSenha(hashService.getHashSenha(request.senha()));
    usuario.setPessoa(pessoa);
    usuario.setRole("FUNCIONARIO");
    usuarioRepository.persist(usuario);

    pessoa.getUsuarios().add(usuario);

    repository.persist(funcionario);
    return FuncionarioResponse.valueOf(funcionario);
  }

  @Override
  @Transactional
  public void update(long id, FuncionarioReduzidoRequest request) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
      throw ValidationException.of("id", "Funcionario nao encontrado.");
    }

    funcionario.setCargo(request.cargo());
    funcionario.setDataAdmissao(request.dataAdmissao());
    funcionario.setSalario(request.salario());

    Pessoa pessoa = funcionario.getPessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
  }
  
  @Override
  @Transactional
  public void update(String email, FuncionarioReduzidoRequest request) {
    Funcionario funcionario = repository.findByEmail(email);
    if (funcionario == null) {
      throw ValidationException.of("email", "Funcionario nao encontrado.");
    }

    funcionario.setCargo(request.cargo());
    funcionario.setDataAdmissao(request.dataAdmissao());
    funcionario.setSalario(request.salario());

    Pessoa pessoa = funcionario.getPessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
  }

  @Override
  @Transactional
  public void delete(Long id) {
    validarFuncionarioId(id);
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void resetarSenha(Long id) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
        throw ValidationException.of("id", "O funcionario com ID " + id + " nao foi encontrado.");
    }

    Usuario usuario = funcionario.getPessoa().getUsuarios().stream()
        .filter(u -> "FUNCIONARIO".equals(u.getRole()))
        .findFirst()
        .orElseThrow(() -> ValidationException.of("usuario", "Usuario 'Funcionario' nao encontrado."));

    String senhaTemporaria = gerarSenhaAleatoria(6);
    String novaHash = hashService.getHashSenha(senhaTemporaria);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarSenha(String email, String novaSenha) {
    Usuario usuario = usuarioRepository.findByEmail(email);
    
    if (usuario == null) {
        throw ValidationException.of("email", "O usuario nao foi encontrado.");
    }
  
    if (novaSenha == null || novaSenha.trim().isEmpty()) {
         throw ValidationException.of("novaSenha", "A nova senha nao pode ser vazia.");
    }
    
    String novaHash = hashService.getHashSenha(novaSenha);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarEmail(String email, String novoEmail) {
    Usuario usuario = usuarioRepository.findByEmail(email);
    
    if (usuario == null) {
        throw ValidationException.of("email", "O usuario nao foi encontrado.");
    }
    
    Usuario usuarioExistente = usuarioRepository.findByEmail(novoEmail);
    if (usuarioExistente != null && usuarioExistente.getId() != usuario.getId()) {
        throw ValidationException.of("novoEmail", "Este novo e-mail ja esta cadastrado.");
    }

    usuario.setEmail(novoEmail);
  }
  
  @Override
  public List<FuncionarioResponse> findAll(Integer page, Integer pageSize) {
    PanacheQuery<Funcionario> query = null;
    if (page == null || pageSize == null) {
      query = repository.findAll();
    } else {
      query = repository.findAll().page(page, pageSize);
    }

    return query.list().stream()
      .map(FuncionarioResponse::valueOf)
      .toList();
  }

  @Override
  public List<FuncionarioResponse> findAll() {
    return repository.findAll().stream()
      .map(FuncionarioResponse::valueOf)
      .toList();
  }

  @Override
  public FuncionarioResponse findById(Long id) {
    validarFuncionarioId(id);
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
      throw ValidationException.of("id", "Funcionario nao encontrado.");
    }
    return FuncionarioResponse.valueOf(funcionario);
  }
  
  @Override
  public UsuarioResponse findEmailbyId(long id) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
        throw ValidationException.of("id", "O funcionario com ID " + id + " nao foi encontrado.");
    }

    String email = funcionario.getPessoa().getUsuarios().stream()
        .filter(usuario -> "FUNCIONARIO".equals(usuario.getRole()))
        .findFirst()
        .map(Usuario::getEmail)
        .orElseThrow(() -> ValidationException.of("email", "Usuario com role 'Funcionario' nao encontrado para este funcionario."));

    return new UsuarioResponse(email);
  }
  
  @Override
  public List<FuncionarioResponse> findByNome(String nome) {
    return repository.findByNome(nome).stream()
      .map(FuncionarioResponse::valueOf)
      .toList();
  }

  @Override
  public FuncionarioResponse findByEmail(String email) {
    Funcionario funcionario = repository.findByEmail(email);
    if(funcionario == null) {
      throw ValidationException.of("email","Funcionario nao encontrado");
    }
    return FuncionarioResponse.valueOf(funcionario);
  }

  @Override
  public Long count() {
    return repository.count();
  }

  @Override
  public Long count(String nome) {
    return repository.count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }

  private String gerarSenhaAleatoria(int length) {
      return "123456";
  }

  public void validarFuncionarioId(Long id) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
      throw ValidationException.of("id", "ID invalido.");
    }
  }

  @Override
  public Long findUsuarioIdByFuncionarioId(Long id) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
      throw ValidationException.of("id", "Funcionario nao encontrado.");
    }
    Usuario usuario = funcionario.getPessoa().getUsuarios().stream()
      .filter(u -> "FUNCIONARIO".equalsIgnoreCase(u.getRole()))
      .findFirst()
      .orElseThrow(() -> ValidationException.of("usuario", "Usuario com role 'Funcionario' nao encontrado."));
    return usuario.getId();
  }

  @Override
  public Long findUsuarioIdByEmail(String email) {
    Funcionario funcionario = repository.findByEmail(email);
    if (funcionario == null) {
      throw ValidationException.of("email", "Funcionario nao encontrado.");
    }
    Usuario usuario = funcionario.getPessoa().getUsuarios().stream()
      .filter(u -> "FUNCIONARIO".equalsIgnoreCase(u.getRole()))
      .findFirst()
      .orElseThrow(() -> ValidationException.of("usuario", "Usuario com role 'Funcionario' nao encontrado."));
    return usuario.getId();
  }
}
