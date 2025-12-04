package br.unitins.guitarra.model.perfil;

import br.unitins.guitarra.model.BaseEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@UserDefinition
public class Usuario extends BaseEntity {
    @Username
    private String email;
    @Password
    private String senha;
    @Roles
    private String role;
    private String nomeImagem;
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
}
