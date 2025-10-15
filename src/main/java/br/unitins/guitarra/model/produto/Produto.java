package br.unitins.guitarra.model.produto;

import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Produto extends BaseEntity{
  @OneToMany
  @JoinColumn(name = "id_avaliacao")
  private List<Avaliacao> avaliacao;
  private String descricao;
  private String imagem;
  @NotBlank(message = "O campo nome é obrigatório" )
  private String nome;
  @NotNull(message = "O campo preco é obrigatório" )
  private Double preco;
  @NotNull(message = "O campo quantidade é obrigatório" )
  private Integer quantidade;
  private Boolean status;
}
 