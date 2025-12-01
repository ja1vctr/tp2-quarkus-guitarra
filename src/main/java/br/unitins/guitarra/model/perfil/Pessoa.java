package br.unitins.guitarra.model.perfil;

import java.time.LocalDate;
import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pessoa extends BaseEntity {
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    @OneToMany()
    @JoinColumn(name = "id_usuario")
    private List<Usuario> usuarios;
}
