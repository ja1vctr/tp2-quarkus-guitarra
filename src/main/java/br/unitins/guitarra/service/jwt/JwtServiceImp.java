package br.unitins.guitarra.service.jwt;

import br.unitins.guitarra.model.perfil.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImp implements JwtService{
    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);
    @Override
    public String generateJwt(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);
        Set<String> roles = new HashSet<String>();
        roles.add(usuario.getRole() != null ? usuario.getRole().toUpperCase() : "");
        return Jwt.issuer("unitins-jwt")
                .subject(usuario.getEmail())
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();
    }
}
