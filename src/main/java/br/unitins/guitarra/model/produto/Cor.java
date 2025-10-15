package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cor extends BaseEntity {
  private String nome;
  private String codigoHexadecimal;
}
