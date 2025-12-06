package br.unitins.guitarra.dto.auth;

import java.util.List;

public record AuthResponse(
        String token,
        AuthUser user,
        List<String> roles
) {}
