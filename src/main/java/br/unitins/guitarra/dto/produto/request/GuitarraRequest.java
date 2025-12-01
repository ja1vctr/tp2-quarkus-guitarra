package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GuitarraRequest(
  // Atributos de Produto
  @NotBlank(message = "O campo nome não pode ser nulo.")
  String nome,
  String descricao,
  @NotNull(message = "O campo preço não pode ser nulo.")
  @Positive(message = "O preço deve ser um valor positivo.")
  Double preco,
  @NotNull(message = "O campo quantidade não pode ser nulo.")
  Integer quantidade,
  @NotNull(message = "O campo status não pode ser nulo.")
  Boolean status,

  // Atributos de Guitarra
  Integer anoFabricacao,
  String madeira,
  @NotNull(message = "O campo numeroDeCordas não pode ser nulo.")
  Integer numeroDeCordas,

  // IDs de Relacionamentos
  @NotNull(message = "O id da marca não pode ser nulo.")
  Long idBraco,
  Long idCaptadorBraco,
  Long idCaptadorMeio,
  Long idCaptadorPonte,
  Long idCor,
  Long idPonte,
  Long idMarca,
  @NotBlank(message = "O campo modelo não pode ser nulo.")
  Long idModelo,
  Long idTarracha
) {}
