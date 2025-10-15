package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tarracha extends BaseEntity{
  private String marca;
  private String material;
  private String modelo;

}
