package br.unitins.guitarra.model.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Guitarra extends Produto{
  private Integer anoFabricacao;
  private String assinatura;
  private Boolean blindagemEletronica;
  @ManyToOne
  @JoinColumn(name = "id_braco")
  private Braco braco;
  @ManyToOne
  @JoinColumn(name = "id_captador_braco")
  private Captador captadorBraco;
  @ManyToOne
  @JoinColumn(name = "id_captador_meio")
  private Captador captadorMeio;
  @ManyToOne
  @JoinColumn(name = "id_captador_ponte")
  private Captador captadorPonte;
  @ManyToOne
  @JoinColumn(name = "id_cor") 
  private Cor cor;
  @ManyToOne
  @JoinColumn(name = "id_marca")
  private Marca marca;
  private String madeira;
  @ManyToOne
  @JoinColumn(name = "id_modelo")
  private Modelo modelo;
  private Integer numeroDeCordas;
  private Double peso;
  @ManyToOne
  @JoinColumn(name = "id_ponte")
  private Ponte ponte;
  @ManyToOne
  @JoinColumn(name = "id_tarracha")
  private Tarracha tarracha;
}
