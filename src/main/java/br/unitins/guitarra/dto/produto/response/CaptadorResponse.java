package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Captador;

public record CaptadorResponse(
    Long id,
    String marca,
    String modelo,
    String posicao
) {
    public static CaptadorResponse valueOf (Captador captador) {
        if (captador == null) return null;
          return new CaptadorResponse(
          captador.getId(),
          captador.getMarca(),
          captador.getModelo(),
          captador.getPosicao()
        );
    }
}