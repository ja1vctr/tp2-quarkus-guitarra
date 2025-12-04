package br.unitins.guitarra.dto.perfil.request;

import java.time.LocalDate;

public record ClienteReduzidoRequest(
  //cliente
  Boolean   permitirMarketing,
  
  //pessoa  
  String    cpf,
  LocalDate dataNascimento,
  String    nome
) {}
