package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

public record ClienteRequest(
  //cliente
  long id,
  Boolean permitirMarketing,
  //pessoa  
  String cpf,
  LocalDate   dataNascimento,
  String nome,
  //usuario
  String email,
  String senha
) {}
