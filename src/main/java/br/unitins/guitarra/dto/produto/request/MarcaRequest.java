package br.unitins.guitarra.dto.produto.request;

import jakarta.validation.constraints.NotBlank;

public record MarcaRequest(
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    @NotBlank(message = "O campo cnpj não pode ser nulo.")
    String cnpj
) {}