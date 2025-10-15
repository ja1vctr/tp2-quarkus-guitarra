package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ponte extends BaseEntity {
  @NotBlank(message = "O campo marca é obrigatório")
  private String marca;
  @NotBlank(message = "O campo modelo é obrigatório")
  private String modelo;
}
