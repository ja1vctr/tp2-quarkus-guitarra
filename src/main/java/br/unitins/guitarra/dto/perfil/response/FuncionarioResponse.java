package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;

import br.unitins.guitarra.model.perfil.Funcionario;

public record FuncionarioResponse(
  Long id,
  LocalDate dataAdmissao,
  String cargo,
  Double salario,
  String cpf,
  LocalDate dataNascimento,
  String nome,
  String email
) {
  public static FuncionarioResponse valueOf (Funcionario funcionario) {
    String emailFuncionario = funcionario.getPessoa()
      .getUsuarios()
      .stream()
      .filter(u -> "FUNCIONARIO".equals(u.getRole()))
      .findFirst()
      .map(u -> u.getEmail())
      .orElse(null);

    return new FuncionarioResponse(
        funcionario.getId(),
        funcionario.getDataAdmissao(),
        funcionario.getCargo(),
        funcionario.getSalario(),
        funcionario.getPessoa().getCpf(),
        funcionario.getPessoa().getDataNascimento(),
        funcionario.getPessoa().getNome(),
        emailFuncionario
    );
  }
}
