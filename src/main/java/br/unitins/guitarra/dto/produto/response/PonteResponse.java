package br.unitins.guitarra.dto.produto.response;

import br.unitins.guitarra.model.produto.Ponte;

public record PonteResponse(
    Long id,
    String marca,
    String modelo
) {
    public static PonteResponse valueOf(Ponte ponte) {
        if (ponte == null) return null;
        
        return new PonteResponse(
            ponte.getId(), 
            ponte.getMarca(), 
            ponte.getModelo()
            );
    }
}