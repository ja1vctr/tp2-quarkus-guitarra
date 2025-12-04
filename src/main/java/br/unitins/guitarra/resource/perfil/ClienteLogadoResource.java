package br.unitins.guitarra.resource.perfil;

import java.util.logging.Logger;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.guitarra.dto.perfil.request.ClienteReduzidoRequest;

import br.unitins.guitarra.service.perfil.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/clienteLogado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteLogadoResource {
    @Inject
    ClienteService service;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(String.valueOf(ClienteLogadoResource.class));

    @GET
    @RolesAllowed({ "Cliente" })
    public Response getClienteLogado() {
        // obtendo o login pelo token jwt
        String email = jwt.getSubject();
        LOG.info("Pegando o usuário logado string: " + email);
        LOG.info("Pegando o usuário logado");
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        return Response.ok(service.findByEmail(email)).build();
    }
    
    @PUT
    // @Path("/{email}")
    @RolesAllowed({ "Cliente" })
    @Transactional
    public Response update(ClienteReduzidoRequest request) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuário logado string: " + email);
        LOG.info("Pegando o usuário logado");
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.update(email, request);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/alterar-senha")
    @RolesAllowed({ "Cliente" })
    @Transactional
    public Response alterarSenha(@QueryParam("novaSenha") String novaSenha) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuário logado string: " + email);
        LOG.info("Pegando o usuário logado");
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.alterarSenha(email, novaSenha);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/alterar-email")
    @RolesAllowed({ "Cliente" })
    @Transactional
    public Response alterarEmail(@QueryParam("novoEmail") String novoEmail) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuário logado string: " + email);
        LOG.info("Pegando o usuário logado");
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.alterarEmail(email, novoEmail);
        return Response.status(Status.NO_CONTENT).build();
    }
}