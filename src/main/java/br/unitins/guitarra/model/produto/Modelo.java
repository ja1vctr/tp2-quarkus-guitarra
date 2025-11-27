package br.unitins.guitarra.model.produto;

import java.util.ArrayList;
import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Modelo extends BaseEntity {
  private String nome;

  @ManyToMany
  @JoinTable(
    name = "marca_modelo",
    joinColumns = @JoinColumn(name = "modelo_id"),
    inverseJoinColumns = @JoinColumn(name = "marca_id")
  )
  private List<Marca> listaMarcas = new ArrayList<Marca>();
}
