package br.unitins.guitarra.dto.perfil;

import javax.xml.crypto.Data;

public record ClienteReduzidoRequest(
  String cpf,
  Data   dataNascimento,
  String email,
  String nome
) {}
