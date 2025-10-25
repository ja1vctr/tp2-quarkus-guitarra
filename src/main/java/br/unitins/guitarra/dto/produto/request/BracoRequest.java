package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BracoRequest(
    @NotBlank(message = "O campo formato não pode ser nulo.")
    String formato,
    @NotBlank(message = "O campo madeira não pode ser nulo.")
    String madeira,
    @NotNull(message = "O campo numero de trastes não pode ser nulo.")
    Integer numeroDeTrastes
) {}