package br.unitins.guitarra.resource;

import java.util.List;
import java.util.Optional;

import br.unitins.guitarra.dto.auth.AuthResponse;
import br.unitins.guitarra.dto.auth.AuthUser;
import br.unitins.guitarra.dto.auth.LoginRequest;
import br.unitins.guitarra.dto.auth.RegisterRequest;
import br.unitins.guitarra.model.perfil.Cliente;
import br.unitins.guitarra.model.perfil.Pessoa;
import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.ClienteRepository;
import br.unitins.guitarra.repository.perfil.PessoaRepository;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.service.hash.HashService;
import br.unitins.guitarra.service.jwt.JwtService;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(@Valid LoginRequest request) {
        String senhaHash = hashService.getHashSenha(request.senha());
        Usuario usuario = usuarioRepository.findByEmailAndSenha(request.email(), senhaHash);

        if (usuario == null) {
            throw ValidationException.of("credenciais", "E-mail ou senha inválidos.");
        }

        return Response.ok(buildAuthResponse(usuario)).build();
    }

    @POST
    @Path("/register")
    @PermitAll
    @Transactional
    public Response register(@Valid RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.email()) != null) {
            throw ValidationException.of("email", "Já existe um usuário com este e-mail.");
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.nome());
        pessoaRepository.persist(pessoa);

        Cliente cliente = new Cliente();
        cliente.setPessoa(pessoa);
        cliente.setPermitirMarketing(Boolean.FALSE);
        clienteRepository.persist(cliente);

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setSenha(hashService.getHashSenha(request.senha()));
        usuario.setRole("CLIENTE");
        usuario.setPessoa(pessoa);
        usuarioRepository.persist(usuario);

        pessoa.getUsuarios().add(usuario);

        AuthResponse response = buildAuthResponse(usuario);
        return Response.status(Status.CREATED).entity(response).build();
    }

    private AuthResponse buildAuthResponse(Usuario usuario) {
        List<String> roles = List.of(Optional.ofNullable(usuario.getRole()).orElse("").toUpperCase());
        AuthUser user = new AuthUser(
                usuario.getId(),
                usuario.getPessoa() != null ? usuario.getPessoa().getNome() : null,
                usuario.getEmail(),
                roles);
        String token = jwtService.generateJwt(usuario);
        return new AuthResponse(token, user, roles);
    }
}
