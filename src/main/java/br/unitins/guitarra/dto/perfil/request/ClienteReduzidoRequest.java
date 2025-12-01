package br.unitins.guitarra.dto.perfil.request;

import javax.xml.crypto.Data;

public record ClienteReduzidoRequest(
  //cliente
  long id,
  Boolean permitirMarketing,
  //pessoa  
  String cpf,
  Data   dataNascimento,
  String nome
) {}
