package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Braco;

public record BracoResponse(
    Long id,
    String formato,
    String madeira,
    Integer numeroDeTrastes
) {
    public static BracoResponse valueOf(Braco braco) {
        if (braco == null) return null;
          return new BracoResponse(
          braco.getId(),
          braco.getFormato(),
          braco.getMadeira(),
          braco.getNumeroDeTrastes()
        );
    }
}