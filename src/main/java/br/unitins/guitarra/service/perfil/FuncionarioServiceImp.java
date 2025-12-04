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
    // Criando um novo funcionario
    Funcionario funcionario = new Funcionario();
    // funcionario.setPermitirMarketing(request.permitirMarketing());

    // Criando uma nova pessoa
    Pessoa pessoa = new Pessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
    pessoaRepository.persist(pessoa);
    funcionario.setPessoa(pessoa);

    // Criando um novo usuário
    Usuario usuario = new Usuario();
    usuario.setEmail(request.email());
    // usuario.setSenha(hashService.getHashSenha(request.senha()));
    usuario.setRole("Funcionario");
    usuarioRepository.persist(usuario);

    // Adicionando o usuário à lista de usuários da pessoa
    pessoa.getUsuarios().add(usuario);

    repository.persist(funcionario);
    return FuncionarioResponse.valueOf(funcionario);
  }

  @Override
  @Transactional
  public void update(long id, FuncionarioReduzidoRequest request) {
    Funcionario funcionario = repository.findById(id);
    // funcionario.setPermitirMarketing(request.permitirMarketing());

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
  public FuncionarioResponse findById(Long id) {
    validarFuncionarioId(id);
    return FuncionarioResponse.valueOf(repository.findById(id));
  }
  
  @Override
  public UsuarioResponse findEmailbyId(long id) {
    // 1. Validar e Buscar o Funcionario
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
        throw ValidationException.of("id", "O funcionario com ID " + id + " não foi encontrado.");
    }

    String email = funcionario.getPessoa().getUsuarios().stream()
        .filter(usuario -> "Funcionario".equals(usuario.getRole()))
        .findFirst()
        .map(Usuario::getEmail) 
        .orElseThrow(() -> ValidationException.of("email", "Usuário com role 'Funcionario' não encontrado para este funcionario."));

    return new UsuarioResponse(email);
  }
  
  @Override
  public void resetarSenha(Long id) {
    Funcionario funcionario = repository.findById(id);
    if (funcionario == null) {
        throw ValidationException.of("id", "O funcionario com ID " + id + " não foi encontrado.");
    }

    Usuario usuario = funcionario.getPessoa().getUsuarios().stream()
        .filter(u -> "Funcionario".equals(u.getRole()))
        .findFirst()
        .orElseThrow(() -> ValidationException.of("usuario", "Usuário 'Funcionario' não encontrado."));

    String senhaTemporaria = gerarSenhaAleatoria(6);
    
    // 4. Gera o hash da senha temporária e atualiza o usuário
    String novaHash = hashService.getHashSenha(senhaTemporaria);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarSenha(FuncionarioRequest request, String novaSenha) {
    Usuario usuario = usuarioRepository.findByEmail(request.email());
    
    if (usuario == null) {
        throw ValidationException.of("email", "O usuário não foi encontrado.");
    }

    String hashSenhaAtual = hashService.getHashSenha(request.senha());
    
    if (!usuario.getSenha().equals(hashSenhaAtual)) {
        throw ValidationException.of("senha", "A senha atual está incorreta.");
    }
  
    if (novaSenha == null || novaSenha.trim().isEmpty()) {
         throw ValidationException.of("novaSenha", "A nova senha não pode ser vazia.");
    }
    
    String novaHash = hashService.getHashSenha(novaSenha);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarEmail(FuncionarioRequest request, String novoEmail) {
    Usuario usuario = usuarioRepository.findByEmail(request.email());
    
    if (usuario == null) {
        throw ValidationException.of("email", "O usuário não foi encontrado.");
    }
    
    Usuario usuarioExistente = usuarioRepository.findByEmail(novoEmail);
    if (usuarioExistente != null && usuarioExistente.getId() != usuario.getId()) {
        throw ValidationException.of("novoEmail", "Este novo e-mail já está cadastrado.");
    }

    usuario.setEmail(novoEmail);
  }

  @Override
  public Long count() {
    return repository.count();
  }

  @Override
  public Long count(String nome) {
    return repository.count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
  }


  @Override
  public List<FuncionarioResponse> findAll(Integer page, Integer pageSize) {
    PanacheQuery<Funcionario> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(FuncionarioResponse::valueOf).
                      toList();
  }

  @Override
  public List<FuncionarioResponse> findAll() {
    return repository.findAll().stream().
                  map(FuncionarioResponse::valueOf).
                  toList();
  }

  @Override
  public List<FuncionarioResponse> findByNome(String nome) {
    return repository.findByNome(nome).stream().
                  map(FuncionarioResponse::valueOf).toList();
  }

  //---------------------Auxiliares---------------------//
  // Adicione este método na classe FuncionarioServiceImp
  private String gerarSenhaAleatoria(int length) {
      return "123456";
      // String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      // StringBuilder senha = new StringBuilder();
      // java.util.Random random = new java.util.Random();
      
      // for (int i = 0; i < length; i++) {
      //     senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
      // }
      // return senha.toString();
  }

  //---------------------Validacoes---------------------//

  public void validarFuncionarioId(Long id) {
    Funcionario  funcionario = repository.findById(id);
    if (funcionario == null)
      throw ValidationException.of("id", "ID inválido.");
  }

}
