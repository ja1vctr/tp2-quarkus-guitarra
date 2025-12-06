package br.unitins.guitarra.dto.auth;

import java.util.List;

public record AuthUser(
        Long id,
        String nome,
        String email,
        List<String> roles
) {}
