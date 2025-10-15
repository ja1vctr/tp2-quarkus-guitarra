package br.unitins.guitarra.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "O campo nao pode ser nulo.")
        String email,
        @NotBlank(message = "O campo nao pode ser nulo.")
        @Size(min = 6, message= "O campo deve ter tamanho mínimo de 6 caracteres")
        String senha
) {
}
