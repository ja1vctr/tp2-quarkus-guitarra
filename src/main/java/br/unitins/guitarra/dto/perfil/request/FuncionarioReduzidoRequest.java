package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

public record FuncionarioReduzidoRequest(
  // Funcionario
  LocalDate dataAdmissao,
  String    cargo,
  Double    salario,
  // Pessoa
  String    cpf,
  String    nome,
  LocalDate dataNascimento
) {}
