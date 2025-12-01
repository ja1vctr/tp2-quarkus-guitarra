package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

public record FuncionarioRequest(
  // Funcionario
  long id,
  LocalDate dataAdmissao,
  String    cargo,
  Double    salario,
  // Pessoa
  String    cpf,
  String    nome,
  LocalDate dataNascimento,
  // Usuario
  String    email,
  String    senha
) {}
