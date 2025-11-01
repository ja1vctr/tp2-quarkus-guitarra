package br.unitins.guitarra.dto.produto.response;

import java.time.LocalDate;

import br.unitins.guitarra.model.produto.Braco;

public record BracoResponse(
    Long id,
    String formato,
    String madeira,
    Integer numeroDeTrastes,
    LocalDate dataDeFabricacao,
    String descricao
) {
    public static BracoResponse valueOf(Braco braco) {
        if (braco == null) return null;
          return new BracoResponse(
          braco.getId(),
          braco.getFormato(),
          braco.getMadeira(),
          braco.getNumeroDeTrastes(),
          braco.getDataDeFabricacao(),
          braco.getDescricao()
        );
    }
}