package br.unitins.guitarra.model.produto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Estrela {
  UM(1, "UM"),
  DOIS(2, "DOIS"),
  TRES(3, "TRES"),
  QUATRO(4, "QUATRO"),
  CINCO(5, "CINCO");

  private final Integer id;
  private final String label;

  Estrela(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public static Estrela valueOf(Integer id) {
    if (id.equals(null))
      return null;

    for (Estrela estrela : Estrela.values()) {
      if (estrela.getId().equals(id)) {
        return estrela;
      }
    }
    throw new IllegalArgumentException("Id de estrela inválido: " + id);
  }
}