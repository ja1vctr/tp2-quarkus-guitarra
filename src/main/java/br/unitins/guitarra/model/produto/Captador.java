package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Captador extends BaseEntity{
  @NotBlank(message = "O campo tipo é obrigatório")
  private String marca;
  @NotBlank(message = "O campo modelo é obrigatório")
  private String modelo;
  @NotBlank(message = "O campo posicao é obrigatório")
  @Enumerated(EnumType.STRING)
  private PosicaoCaptador posicao;
}
