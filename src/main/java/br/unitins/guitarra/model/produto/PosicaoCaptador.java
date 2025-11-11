package br.unitins.guitarra.model.produto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PosicaoCaptador {
  BRACO(1, "BRACO"),
  MEIO(2, "MEIO"),
  PONTE(3, "PONTE");

  private final Integer id;
  private final String label;

  PosicaoCaptador(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public static PosicaoCaptador valueOf(Integer id) throws IllegalArgumentException{
    for (PosicaoCaptador posicaoCaptador : PosicaoCaptador.values()){
      if(id == posicaoCaptador.getId())
        return posicaoCaptador;
    }
    throw new IllegalArgumentException("Id de posicaoCaptador inválido: " + id);
  }
}
