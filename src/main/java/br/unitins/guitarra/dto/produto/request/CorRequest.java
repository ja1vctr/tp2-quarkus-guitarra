package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;

public record CorRequest(
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    @NotBlank(message = "O campo codigoHexadecimal não pode ser nulo.")
    String codigoHexadecimal
) {}