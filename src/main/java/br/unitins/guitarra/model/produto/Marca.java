package br.unitins.guitarra.model.produto;

import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
  @ManyToMany
  @JoinTable(
    name = "marca_modelo",
    joinColumns = @JoinColumn(name = "marca_id"),
    inverseJoinColumns = @JoinColumn(name = "modelo_id")
  )
  private List<Modelo> listaModelos;
}
