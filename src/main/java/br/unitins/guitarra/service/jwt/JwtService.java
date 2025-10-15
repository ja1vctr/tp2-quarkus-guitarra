package br.unitins.guitarra.service.jwt;

import br.unitins.guitarra.model.perfil.Usuario;

public interface JwtService {
    public String generateJwt(Usuario usuario);
}