package br.unitins.guitarra.model.perfil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.unitins.guitarra.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Usuario> usuarios =  new ArrayList<>();
}
