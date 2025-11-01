package br.unitins.guitarra.model.produto;

import java.time.LocalDate;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Braco extends BaseEntity{
  private String formato;
  private String madeira;
  private Integer numeroDeTrastes;
  private LocalDate dataDeFabricacao;
  private String descricao;

}
