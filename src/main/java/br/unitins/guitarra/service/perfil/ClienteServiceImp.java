package br.unitins.guitarra.service.perfil;

import br.unitins.guitarra.dto.perfil.request.ClienteRequest;
import br.unitins.guitarra.dto.perfil.response.ClienteResponse;
import br.unitins.guitarra.model.perfil.Cliente;
import br.unitins.guitarra.model.perfil.Pessoa;
import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.ClienteRepository;
import br.unitins.guitarra.repository.perfil.PessoaRepository;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.service.hash.HashService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class ClienteServiceImp implements ClienteService {
  @Inject
  ClienteRepository clienteRepository;

  @Inject
  PessoaRepository pessoaRepository;

  @Inject
  UsuarioRepository usuarioRepository;

  @Inject
  HashService hashService;

  @Override
  public Cliente findByEmailAndSenha(String email, String senha) {
    return clienteRepository.findByEmailAndSenha(email, senha);
  }

  @Override
  @Transactional
  public Cliente create(ClienteRequest request) {
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
    usuario.setRole("Cliente");
    usuarioRepository.persist(usuario);

    // Adicionando o usuário à lista de usuários da pessoa
    pessoa.getUsuarios().add(usuario);

    clienteRepository.persist(cliente);
    return cliente;
  }

  @Override
  @Transactional
  public void update(ClienteRequest request) {
    Cliente cliente = clienteRepository.findById(request.id());
    cliente.setPermitirMarketing(request.permitirMarketing());

    Pessoa pessoa = cliente.getPessoa();
    pessoa.setCpf(request.cpf());
    pessoa.setDataNascimento(request.dataNascimento());
    pessoa.setNome(request.nome());

    // Atualizar o usuário associado, se existir
    Usuario usuario = pessoa.getUsuarios().stream()
        .filter(u -> u.getRole().equals("Cliente"))
        .findFirst()
        .orElse(null);

    if (usuario != null) {
      usuario.setEmail(request.email());
      // A senha não é atualizada aqui, pois há um método específico para isso
    }
  }

  @Override
  @Transactional
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public ClienteResponse findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public void alterarSenha(ClienteRequest request, String novaSenha) {
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
