package br.unitins.guitarra.resource.perfil;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.guitarra.dto.perfil.request.ClienteReduzidoRequest;
import br.unitins.guitarra.service.perfil.ClienteService;
import br.unitins.guitarra.service.storage.UsuarioStorageService;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
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

    @Inject
    UsuarioStorageService usuarioStorageService;

    private static final Logger LOG = Logger.getLogger(String.valueOf(ClienteLogadoResource.class));
    private static final Set<String> ALLOWED_TYPES = Set.of(
        "image/png",
        "image/jpeg",
        "image/jpg",
        "image/gif"
    );

    @GET
    @RolesAllowed({ "CLIENTE" })
    public Response getClienteLogado() {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuario logado string: " + email);
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        return Response.ok(service.findByEmail(email)).build();
    }
    
    @PUT
    @RolesAllowed({ "CLIENTE" })
    @Transactional
    public Response update(ClienteReduzidoRequest request) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuario logado string: " + email);
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.update(email, request);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/alterar-senha")
    @RolesAllowed({ "CLIENTE" })
    @Transactional
    public Response alterarSenha(@QueryParam("novaSenha") String novaSenha) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuario logado string: " + email);
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.alterarSenha(email, novaSenha);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/alterar-email")
    @RolesAllowed({ "CLIENTE" })
    @Transactional
    public Response alterarEmail(@QueryParam("novoEmail") String novoEmail) {
        String email = jwt.getSubject();
        LOG.info("Pegando o usuario logado string: " + email);
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        service.alterarEmail(email, novoEmail);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/imagem")
    @RolesAllowed({ "CLIENTE" })
    @Produces({"image/png", "image/jpeg", "image/jpg", "image/gif"})
    public Response getImageClienteLogado() {
        String email = jwt.getSubject();
        if(email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        try {
            Long usuarioId = service.findUsuarioIdByEmail(email);
            var stream = usuarioStorageService.getPrivateImage(usuarioId);
            return Response.ok(stream).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem nao encontrada.").build();
        }
    }

    @POST
    @Path("/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "CLIENTE" })
    public Response uploadImageClienteLogado(@RestForm("file") FileUpload file) {
        String email = jwt.getSubject();
        if (email == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        try {
            Long usuarioId = service.findUsuarioIdByEmail(email);
            if (file == null) {
                throw ValidationException.of("file", "Arquivo e obrigatorio.");
            }
            if (!ALLOWED_TYPES.contains(file.contentType())) {
                throw ValidationException.of("file", "Formato de imagem nao permitido.");
            }
            if (file.size() > 10 * 1024 * 1024) {
                throw ValidationException.of("file", "Arquivo excede 10MB.");
            }

            InputStream is = Files.newInputStream(file.uploadedFile());
            usuarioStorageService.uploadImagem(usuarioId, is);

            return Response.ok("Upload realizado").build();
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            return Response.serverError().entity("Erro: " + e.getMessage()).build();
        }
    }
}
