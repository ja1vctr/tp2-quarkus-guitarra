package br.unitins.guitarra.dto.produto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ModeloRequest(
  @NotBlank(message = "O campo nome não pode ser nulo.")
  String nome,
  @NotEmpty(message = "A lista de marcas não pode ser vazia")
  List<Long> idMarcas
  ) {}
