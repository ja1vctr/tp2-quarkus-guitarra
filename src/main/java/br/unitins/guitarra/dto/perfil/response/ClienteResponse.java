package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;

import br.unitins.guitarra.model.perfil.Cliente;

public record ClienteResponse(
  //cliente
  Boolean permitirMarketing,
  //pessoa  
  String cpf,
  LocalDate dataNascimento,
  String nome

) {
  public static ClienteResponse valueOf (Cliente cliente) {

    return new ClienteResponse(
        cliente.getPermitirMarketing(),
        cliente.getPessoa().getCpf(),
        cliente.getPessoa().getDataNascimento(),
        cliente.getPessoa().getNome()
    );
  }
}
