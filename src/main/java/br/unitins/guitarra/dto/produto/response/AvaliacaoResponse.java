package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.perfil.Cliente;
import br.unitins.guitarra.model.produto.Avaliacao;

public record AvaliacaoResponse(
    Long id,
    String comentario,
    Cliente cliente
) {
    public static AvaliacaoResponse valueOf(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
          avaliacao.getId(),
          avaliacao.getComentario(),
          avaliacao.getCliente()
        );
    }
}