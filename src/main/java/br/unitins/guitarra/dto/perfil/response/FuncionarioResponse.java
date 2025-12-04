package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;

import br.unitins.guitarra.model.perfil.Funcionario;

public record FuncionarioResponse(
  //funcionario
  LocalDate dataAdmissao,
  String cargo,
  Double salario,
  //pessoa  
  String cpf,
  LocalDate dataNascimento,
  String nome
) {
  public static FuncionarioResponse valueOf (Funcionario funcionario) {

    return new FuncionarioResponse(
        funcionario.getDataAdmissao(),
        funcionario.getCargo(),
        funcionario.getSalario(),
        funcionario.getPessoa().getCpf(),
        funcionario.getPessoa().getDataNascimento(),
        funcionario.getPessoa().getNome()
    );
  }
}
