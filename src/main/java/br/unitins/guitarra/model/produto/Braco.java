package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Braco extends BaseEntity{
  @NotBlank(message = "O campo formato é obrigatório" )
  private String formato;
  private String madeira;
  private Integer numeroDeTrastes;
}
