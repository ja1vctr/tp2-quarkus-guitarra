package br.unitins.guitarra.dto.perfil.request;

import javax.xml.crypto.Data;

public record FuncionarioReduzidoRequest(
  // Funcionario
  Data   dataAdmissao,
  String cargo,
  Double salario,
  // Pessoa
  String cpf,
  String nome,
  Data   dataNascimento
) {}
