package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Guitarra;

public record GuitarraResponse(
    // Atributos de Produto
    Long id,
    String nome,
    String descricao,
    Double preco,
    Integer quantidade,
    Boolean status,
    String imagem,

    // Atributos de Guitarra
    Integer anoFabricacao,
    String madeira,
    Integer numeroDeCordas,

    // Relacionamentos
    BracoResponse braco,
    CaptadorResponse captadorBraco,
    CaptadorResponse captadorMeio,
    CaptadorResponse captadorPonte,
    CorResponse cor,
    MarcaResponse marca,
    ModeloResponse modelo,
    PonteResponse ponte,
    TarrachaResponse tarracha
) {

    public static GuitarraResponse valueOf(Guitarra guitarra) {
        return new GuitarraResponse(
            // Atributos de Produto
            guitarra.getId(),
            guitarra.getNome(),
            guitarra.getDescricao(),
            guitarra.getPreco(),
            guitarra.getQuantidade(),
            guitarra.getStatus(),
            guitarra.getImagem(),
            // Atributos de Guitarra
            guitarra.getAnoFabricacao(),
            guitarra.getMadeira(),
            guitarra.getNumeroDeCordas(),
            // Relacionamentos
            // AvaliacaoResponse.valueOf(guitarra.getAvaliacao()),
            BracoResponse.valueOf(guitarra.getBraco()),
            CaptadorResponse.valueOf(guitarra.getCaptadorBraco()),
            CaptadorResponse.valueOf(guitarra.getCaptadorMeio()),
            CaptadorResponse.valueOf(guitarra.getCaptadorPonte()),
            CorResponse.valueOf(guitarra.getCor()),
            MarcaResponse.valueOf(guitarra.getMarca()),
            ModeloResponse.valueOf(guitarra.getModelo()),
            PonteResponse.valueOf(guitarra.getPonte()),
            TarrachaResponse.valueOf(guitarra.getTarracha())
        );
    }
}
