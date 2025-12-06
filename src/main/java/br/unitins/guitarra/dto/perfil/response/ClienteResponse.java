package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;

import br.unitins.guitarra.model.perfil.Cliente;

public record ClienteResponse(
  Long id,
  Boolean permitirMarketing,
  String cpf,
  LocalDate dataNascimento,
  String nome,
  String email
) {
  public static ClienteResponse valueOf (Cliente cliente) {
    String emailCliente = cliente.getPessoa()
      .getUsuarios()
      .stream()
      .filter(u -> "CLIENTE".equals(u.getRole()))
      .findFirst()
      .map(u -> u.getEmail())
      .orElse(null);

    return new ClienteResponse(
        cliente.getId(),
        cliente.getPermitirMarketing(),
        cliente.getPessoa().getCpf(),
        cliente.getPessoa().getDataNascimento(),
        cliente.getPessoa().getNome(),
        emailCliente
    );
  }
}
