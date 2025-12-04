package br.unitins.guitarra.dto.perfil.response;

import br.unitins.guitarra.model.perfil.Usuario;

public record UsuarioResponse(String email) {
  public static UsuarioResponse valueOf(Usuario usuario) {
    return new UsuarioResponse(usuario.getEmail());
  }
}
