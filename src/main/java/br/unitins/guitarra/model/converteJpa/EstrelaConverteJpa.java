package br.unitins.guitarra.model.converteJpa;

import br.unitins.guitarra.model.produto.Estrela;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstrelaConverteJpa implements AttributeConverter<Estrela, Integer>{
  @Override
    public Integer convertToDatabaseColumn(Estrela estrela) {
        return estrela == null ? null : estrela.getId();
    }

    @Override
    public Estrela convertToEntityAttribute(Integer id) {
        return Estrela.valueOf(id);
    }
}
