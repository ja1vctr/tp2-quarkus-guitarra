package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;

public record PonteRequest(
    @NotBlank(message = "O campo marca não pode ser nulo.")
    String marca,
    @NotBlank(message = "O campo modelo não pode ser nulo.")
    String modelo
) {}