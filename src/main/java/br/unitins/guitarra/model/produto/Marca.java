package br.unitins.guitarra.model.produto;

import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Marca extends BaseEntity {
  @NotBlank(message = "O campo nome é obrigatório" )
  private String nome;
  @NotBlank(message = "O campo cnpj é obrigatório" )
  private String cnpj;
  @ManyToMany(mappedBy = "listaMarcas")
  private List<Modelo> listaModelos;
}
