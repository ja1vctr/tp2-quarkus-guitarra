package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequest(
    @NotBlank(message = "O campo comentário não pode ser nulo.")
    String comentario,
    @NotNull(message = "O id do cliente não pode ser nulo.")
    Long idCliente
) {}