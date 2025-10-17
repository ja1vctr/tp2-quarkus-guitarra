package br.unitins.guitarra.dto.produto.response;

import java.util.List;

import br.unitins.guitarra.model.produto.Marca;

public record MarcaResponse(
    Long id,
    String nome,
    String cnpj,
    List<ModeloResponse> listaModelos
) {
    public static MarcaResponse valueOf(Marca marca){
        if (marca == null) return null;
          return new MarcaResponse(
            marca.getId(), 
            marca.getNome(),
            marca.getCnpj(),
            marca.getListaModelos().stream().map(ModeloResponse::valueOf).toList()
        );
    }
}