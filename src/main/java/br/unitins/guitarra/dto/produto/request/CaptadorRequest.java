package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;

public record CaptadorRequest(
    @NotBlank(message = "O campo marca não pode ser nulo.")
    String marca,
    @NotBlank(message = "O campo modelo não pode ser nulo.")
    String modelo,
    @NotBlank(message = "O campo posicao não pode ser nulo.")
    Integer posicao
) {}