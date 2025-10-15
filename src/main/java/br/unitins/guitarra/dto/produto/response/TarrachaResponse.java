package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Tarracha;

public record TarrachaResponse(
    Long id,
    String marca,
    String material,
    String modelo
) {
    public static TarrachaResponse valueOf(Tarracha tarracha) {
        if (tarracha == null) return null;
          return new TarrachaResponse(
          tarracha.getId(), 
          tarracha.getMarca(), 
          tarracha.getMaterial(), 
          tarracha.getModelo());
    }
}