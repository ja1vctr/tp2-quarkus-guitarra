package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Modelo;

public record ModeloResponse(
    Long id,
    String nome)
    {
    public static ModeloResponse valueOf(Modelo modelo) {
      if (modelo == null) return null;
      return new ModeloResponse(
          modelo.getId(),
          modelo.getNome()
      );
  }
}
