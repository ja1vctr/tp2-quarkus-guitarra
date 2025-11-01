package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CorRequest(
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    @NotBlank(message = "O campo codigoHexadecimal não pode ser nulo.")
    @Pattern(
        regexp = "^#?([a-fA-F0-9]{6})$",
        message = "O código hexadecimal deve ter 6 dígitos (0-9, A-F)"
    )
    String codigoHexadecimal
) {}