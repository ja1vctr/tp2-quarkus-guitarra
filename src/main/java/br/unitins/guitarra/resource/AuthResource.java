package br.unitins.guitarra.resource;

import br.unitins.guitarra.dto.auth.AuthRequest;
import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.service.hash.HashService;
import br.unitins.guitarra.service.jwt.JwtService;
import br.unitins.guitarra.service.perfil.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JwtService jwtService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login (AuthRequest authRequest){
        String hashSenha = hashService.getHashSenha(authRequest.senha());
        Usuario usuario  = usuarioService.findByEmailAndSenha(authRequest.email(), hashSenha);

        if(usuario == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        
        return Response.ok("Usuário autenticado com sucesso").
                        header("Authorization", jwtService.generateJwt(usuario)).
                        build();
    }
}
