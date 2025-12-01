package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;
import java.util.Optional;

import br.unitins.guitarra.model.perfil.Cliente;
import br.unitins.guitarra.model.perfil.Usuario;

public record ClienteResponse(
  //cliente
  Boolean permitirMarketing,
  //pessoa  
  String cpf,
  LocalDate dataNascimento,
  String nome,
  //usuario
  String email
) {
  public static ClienteResponse valueOf (Cliente cliente) {
    // 1. Lógica para encontrar o Usuario que tem a role "Cliente"
        Optional<Usuario> usuarioCliente = cliente.getPessoa().getUsuarios().stream()
            .filter(usuario -> {
                return usuario.getRole().contains("Cliente"); 
            })
            .findFirst(); 

        String emailCliente = usuarioCliente.isPresent() 
                                ? usuarioCliente.get().getEmail() 
                                : null;

    return new ClienteResponse(
        cliente.getPermitirMarketing(),
        cliente.getPessoa().getCpf(),
        cliente.getPessoa().getDataNascimento(),
        cliente.getPessoa().getNome(),
        emailCliente
    );
  }
}
