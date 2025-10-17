package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;

public record ModeloRequest(
  @NotBlank(message = "O campo nome não pode ser nulo.")
  String nome
  ) {}
