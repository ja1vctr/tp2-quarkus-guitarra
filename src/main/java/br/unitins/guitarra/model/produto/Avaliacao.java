package br.unitins.guitarra.model.produto;

import br.unitins.guitarra.model.BaseEntity;
import br.unitins.guitarra.model.perfil.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avaliacao extends BaseEntity{
  private String comentario;
  @OneToOne
  @JoinColumn(name = "id_cliente")
  private Cliente cliente;
}
