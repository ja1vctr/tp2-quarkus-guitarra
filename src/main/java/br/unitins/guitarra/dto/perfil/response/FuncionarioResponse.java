package br.unitins.guitarra.dto.perfil.response;

import java.time.LocalDate;
import java.util.Optional;

import br.unitins.guitarra.model.perfil.Funcionario;
import br.unitins.guitarra.model.perfil.Usuario;

public record FuncionarioResponse(
  //funcionario
  LocalDate dataAdmissao,
  String cargo,
  Double salario,
  //pessoa  
  String cpf,
  LocalDate dataNascimento,
  String nome,
  //usuario
  String email
) {
  public static FuncionarioResponse valueOf (Funcionario funcionario) {
    // 1. Lógica para encontrar o Usuario que tem a role "Funcionario"
        Optional<Usuario> usuarioFuncionario = funcionario.getPessoa().getUsuarios().stream()
            .filter(usuario -> {
                return usuario.getRole().contains("Funcionario"); 
            })
            .findFirst(); 

        String emailFuncionario = usuarioFuncionario.isPresent() 
                                ? usuarioFuncionario.get().getEmail() 
                                : null;

    return new FuncionarioResponse(
        funcionario.getDataAdmissao(),
        funcionario.getCargo(),
        funcionario.getSalario(),
        funcionario.getPessoa().getCpf(),
        funcionario.getPessoa().getDataNascimento(),
        funcionario.getPessoa().getNome(),
        emailFuncionario
    );
  }
}
