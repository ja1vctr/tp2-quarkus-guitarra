package br.unitins.guitarra.dto.perfil;

import javax.xml.crypto.Data;

public record FuncionarioReduzidoRequest(
  String cpf,
  Data   dataNascimento,
  Data   dataAdmissao,
  String cargo,
  Double salario,
  String email,
  String nome
) {}
