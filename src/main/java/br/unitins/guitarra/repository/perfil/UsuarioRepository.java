package br.unitins.guitarra.repository.perfil;

import br.unitins.guitarra.model.perfil.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByEmailAndSenha ( String email, String senha){
        return find("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.senha = ?2", email, senha).firstResult();
    }

    public Usuario findByEmail(String email) {
        return find("SELECT u FROM Usuario u WHERE u.email = ?1", email).firstResult();
    }
}   
