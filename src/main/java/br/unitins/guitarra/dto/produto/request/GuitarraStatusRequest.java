package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotNull;

public record GuitarraStatusRequest(
    @NotNull(message = "O status não pode ser nulo.")
    Boolean status
) {}
