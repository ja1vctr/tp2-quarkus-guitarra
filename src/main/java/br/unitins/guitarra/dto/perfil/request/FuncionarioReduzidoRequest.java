package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FuncionarioReduzidoRequest(
  // Funcionario
  @NotNull(message = "O campo dataAdmissao é obrigatório.")
  @PastOrPresent(message = "A data de admissão não pode ser futura.")
  LocalDate dataAdmissao,
  @NotBlank(message = "O campo cargo é obrigatório.")
  String cargo,
  @NotNull(message = "O campo salario é obrigatório.")
  @Positive(message = "O salario deve ser positivo.")
  Double salario,
  // Pessoa
  @NotBlank(message = "O campo cpf é obrigatório.")
  @Size(min = 11, max = 11, message = "O cpf deve conter 11 dígitos numéricos.")
  String cpf,
  @NotBlank(message = "O campo nome é obrigatório.")
  String nome,
  @NotNull(message = "O campo dataNascimento é obrigatório.")
  @PastOrPresent(message = "A data de nascimento não pode ser futura.")
  LocalDate dataNascimento
) {}
