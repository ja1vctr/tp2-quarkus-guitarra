package br.unitins.guitarra.dto.produto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaptadorRequest(
    @NotBlank(message = "O campo marca não pode ser nulo.")
    String marca,
    @NotBlank(message = "O campo modelo não pode ser nulo.")
    String modelo,
    @NotNull(message = "O campo posicao não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    Integer posicao
) {}