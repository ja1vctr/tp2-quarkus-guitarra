package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Captador extends BaseEntity{
  private String marca;
  private String modelo;
  @Enumerated(EnumType.STRING)
  private PosicaoCaptador posicao;
}
