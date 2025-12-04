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
    // Criando um novo cliente
    Cliente cliente = new Cliente();
    cliente.setPermitirMarketing(request.permitirMarketing());

    // Criando uma nova pessoa
    Pessoa pessoa = new Pessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());
    pessoaRepository.persist(pessoa);
    cliente.setPessoa(pessoa);

    // Criando um novo usuário
    Usuario usuario = new Usuario();
    usuario.setEmail(request.email());
    usuario.setSenha(hashService.getHashSenha(request.senha()));
    usuario.setPessoa(pessoa);
    usuario.setRole("Cliente");
    usuarioRepository.persist(usuario);

    // Adicionando o usuário à lista de usuários da pessoa
    pessoa.getUsuarios().add(usuario);

    repository.persist(cliente);
    return ClienteResponse.valueOf(cliente);
  }

  @Override
  @Transactional
  public void update(long id, ClienteReduzidoRequest request) {
    Cliente cliente = repository.findById(id);
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
  public void resetarSenha(Long id) {
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
        throw ValidationException.of("id", "O cliente com ID " + id + " não foi encontrado.");
    }

    Usuario usuario = cliente.getPessoa().getUsuarios().stream()
        .filter(u -> "Cliente".equals(u.getRole()))
        .findFirst()
        .orElseThrow(() -> ValidationException.of("usuario", "Usuário 'Cliente' não encontrado."));

    String senhaTemporaria = gerarSenhaAleatoria(6);

    String novaHash = hashService.getHashSenha(senhaTemporaria);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarSenha(String email, String novaSenha) {
    Usuario usuario = usuarioRepository.findByEmail(email);
    
    if (usuario == null) {
        throw ValidationException.of("email", "O usuário não foi encontrado.");
    }
  
    if (novaSenha == null || novaSenha.trim().isEmpty()) {
         throw ValidationException.of("novaSenha", "A nova senha não pode ser vazia.");
    }
    
    String novaHash = hashService.getHashSenha(novaSenha);
    usuario.setSenha(novaHash);
  }
  
  @Override
  @Transactional
  public void alterarEmail(String email, String novoEmail) {
    Usuario usuario = usuarioRepository.findByEmail(email);
    
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
  public List<ClienteResponse> findAll(Integer page, Integer pageSize) {
    PanacheQuery<Cliente> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(ClienteResponse::valueOf).
                      toList();
  }

  @Override
  public List<ClienteResponse> findAll() {
    return repository.findAll().stream().
                  map(ClienteResponse::valueOf).
                  toList();
  }

  @Override
  public ClienteResponse findById(Long id) {
    validarClienteId(id);
    return ClienteResponse.valueOf(repository.findById(id));
  }
  
  @Override
  public UsuarioResponse findEmailbyId(long id) {
    // 1. Validar e Buscar o Cliente
    Cliente cliente = repository.findById(id);
    if (cliente == null) {
        throw ValidationException.of("id", "O cliente com ID " + id + " não foi encontrado.");
    }

    // 2. Filtrar o usuário com a role "Cliente"
    String email = cliente.getPessoa().getUsuarios().stream()
        .filter(usuario -> "Cliente".equals(usuario.getRole())) // Usa .equals() para comparação exata de role
        .findFirst()
        .map(Usuario::getEmail) // Mapeia para o email se o Optional estiver presente
        .orElseThrow(() -> ValidationException.of("email", "Usuário com role 'Cliente' não encontrado para este cliente."));

    // 3. Retorna o Response DTO (Assumindo que UsuarioResponse é um record com um campo 'email')
    return new UsuarioResponse(email);
  }
  
  @Override
  public List<ClienteResponse> findByNome(String nome) {
    return repository.findByNome(nome).stream().
                  map(ClienteResponse::valueOf).toList();
  }

  @Override
  public ClienteResponse findByEmail(String email) {
    Cliente cliente = repository.findByEmail(email);
    if(cliente == null)
      throw ValidationException.of("email"," cliente não encontrado");
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


  //---------------------Auxiliares---------------------//
  // Adicione este método na classe ClienteServiceImp
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

  public void validarClienteId(Long id) {
    Cliente  cliente = repository.findById(id);
    if (cliente == null)
      throw ValidationException.of("id", "ID inválido.");
  }

}
