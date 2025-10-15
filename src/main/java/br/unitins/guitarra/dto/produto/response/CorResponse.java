package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Cor;

public record CorResponse(
    Long id,
    String nome,
    String codigoHexadecimal
) {
    public static CorResponse valueOf(Cor cor){
        if (cor == null) return null;
          return new CorResponse(
                cor.getId(), 
                cor.getNome(),
                cor.getCodigoHexadecimal()
        );
    }
}