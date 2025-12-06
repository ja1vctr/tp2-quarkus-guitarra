package br.unitins.guitarra.service.perfil;

import java.util.List;

import br.unitins.guitarra.dto.perfil.request.ClienteReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.ClienteRequest;
import br.unitins.guitarra.dto.perfil.response.ClienteResponse;
import br.unitins.guitarra.dto.perfil.response.UsuarioResponse;
import br.unitins.guitarra.model.perfil.Cliente;
import br.unitins.guitarra.model.perfil.Pessoa;
import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.ClienteRepository;
import br.unitins.guitarra.repository.perfil.PessoaRepository;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.service.hash.HashService;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImp implements ClienteService {
  @Inject
  ClienteRepository repository;

  @Inject
  PessoaRepository pessoaRepository;

  @Inject
  UsuarioRepository usuarioRepository;

  @Inject
  HashService hashService;

  @Override
  public Cliente findByEmailAndSenha(String email, String senha) {
    return repository.findByEmailAndSenha(email, senha).firstResult();
  }

  @Override
  @Transactional
  public ClienteResponse create(ClienteRequest request) {
    if (request == null) {
      throw ValidationException.of("request", "Dados do cliente sao obrigatorios.");
    }
    if (usuarioRepository.findByEmail(request.email()) != null) {
      throw ValidationException.of("email", "Ja existe um usuario com este e-mail.");
    }

    Cliente cliente = new Cliente();
    cliente.setPermitirMarketing(request.permitirMarketing());

    Pessoa pessoa = new Pessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
    pessoaRepository.persist(pessoa);
    cliente.setPessoa(pessoa);

    Usuario usuario = new Usuario();
    usuario.setEmail(request.email());
    usuario.setSenha(hashService.getHashSenha(request.senha()));
    usuario.setPessoa(pessoa);
    usuario.setRole("CLIENTE");
    usuarioRepository.persist(usuario);

    pessoa.getUsuarios().add(usuario);

    repository.persist(cliente);
    return ClienteResponse.valueOf(cliente);
  }

  @Override
  @Transactional
  public void update(long id, ClienteReduzidoRequest request) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
      throw ValidationException.of("id", "Cliente nao encontrado.");
    }

    cliente.setPermitirMarketing(request.permitirMarketing());

    Pessoa pessoa = cliente.getPessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
  }
  
  @Override
  @Transactional
  public void update(String email, ClienteReduzidoRequest request) {
    Cliente cliente = repository.findByEmail(email);
    if (cliente == null) {
      throw ValidationException.of("email", "Cliente nao encontrado.");
    }

    cliente.setPermitirMarketing(request.permitirMarketing());

    Pessoa pessoa = cliente.getPessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
  }

  @Override
  @Transactional
  public void delete(Long id) {
    validarClienteId(id);
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void resetarSenha(Long id) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
        throw ValidationException.of("id", "O cliente com ID " + id + " nao foi encontrado.");
    }

    Usuario usuario = cliente.getPessoa().getUsuarios().stream()
        .filter(u -> "CLIENTE".equals(u.getRole()))
        .findFirst()
        .orElseThrow(() -> ValidationException.of("usuario", "Usuario 'Cliente' nao encontrado."));

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
  public List<ClienteResponse> findAll(Integer page, Integer pageSize) {
    PanacheQuery<Cliente> query = null;
    if (page == null || pageSize == null) {
      query = repository.findAll();
    } else {
      query = repository.findAll().page(page, pageSize);
    }

    return query.list().stream()
      .map(ClienteResponse::valueOf)
      .toList();
  }

  @Override
  public List<ClienteResponse> findAll() {
    return repository.findAll().stream()
      .map(ClienteResponse::valueOf)
      .toList();
  }

  @Override
  public ClienteResponse findById(Long id) {
    validarClienteId(id);
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
      throw ValidationException.of("id", "Cliente nao encontrado.");
    }
    return ClienteResponse.valueOf(cliente);
  }
  
  @Override
  public UsuarioResponse findEmailbyId(long id) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
        throw ValidationException.of("id", "O cliente com ID " + id + " nao foi encontrado.");
    }

    String email = cliente.getPessoa().getUsuarios().stream()
        .filter(usuario -> "CLIENTE".equals(usuario.getRole()))
        .findFirst()
        .map(Usuario::getEmail)
        .orElseThrow(() -> ValidationException.of("email", "Usuario com role 'Cliente' nao encontrado para este cliente."));

    return new UsuarioResponse(email);
  }
  
  @Override
  public List<ClienteResponse> findByNome(String nome) {
    return repository.findByNome(nome).stream()
      .map(ClienteResponse::valueOf)
      .toList();
  }

  @Override
  public ClienteResponse findByEmail(String email) {
    Cliente cliente = repository.findByEmail(email);
    if(cliente == null) {
      throw ValidationException.of("email","Cliente nao encontrado");
    }
    return ClienteResponse.valueOf(cliente);
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

  public void validarClienteId(Long id) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
      throw ValidationException.of("id", "ID invalido.");
    }
  }

  @Override
  public Long findUsuarioIdByClienteId(Long id) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
      throw ValidationException.of("id", "Cliente nao encontrado.");
    }
    Usuario usuario = cliente.getPessoa().getUsuarios().stream()
      .filter(u -> "CLIENTE".equals(u.getRole()))
      .findFirst()
      .orElseThrow(() -> ValidationException.of("usuario", "Usuario com role 'Cliente' nao encontrado."));
    return usuario.getId();
  }

  @Override
  public Long findUsuarioIdByEmail(String email) {
    Cliente cliente = repository.findByEmail(email);
    if (cliente == null) {
      throw ValidationException.of("email", "Cliente nao encontrado.");
    }
    Usuario usuario = cliente.getPessoa().getUsuarios().stream()
      .filter(u -> "CLIENTE".equals(u.getRole()))
      .findFirst()
      .orElseThrow(() -> ValidationException.of("usuario", "Usuario com role 'Cliente' nao encontrado."));
    return usuario.getId();
  }
}
