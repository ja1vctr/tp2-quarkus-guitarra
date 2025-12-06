package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record ClienteReduzidoRequest(
  // cliente
  @NotNull(message = "O campo permitirMarketing é obrigatório.")
  Boolean permitirMarketing,

  // pessoa
  @NotBlank(message = "O campo cpf é obrigatório.")
  @Size(min = 11, max = 11, message = "O cpf deve conter 11 dígitos numéricos.")
  String cpf,
  @NotNull(message = "O campo dataNascimento é obrigatório.")
  @PastOrPresent(message = "A data de nascimento não pode ser futura.")
  LocalDate dataNascimento,
  @NotBlank(message = "O campo nome é obrigatório.")
  String nome
) {}
