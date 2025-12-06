package br.unitins.guitarra.resource.perfil;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.guitarra.dto.perfil.request.ClienteReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.ClienteRequest;
import br.unitins.guitarra.dto.perfil.response.ClienteResponse;
import br.unitins.guitarra.service.perfil.ClienteService;
import br.unitins.guitarra.service.storage.UsuarioStorageService;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {
    @Inject
    ClienteService service;

    @Inject
    UsuarioStorageService usuiarioStorageService;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/jpg",
            "image/gif"
    );


    @POST
    @RolesAllowed({"FUNCIONARIO"})
    public Response create(@Valid ClienteRequest request) {
        ClienteResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response update(@PathParam("id") Long id, @Valid ClienteReduzidoRequest request) {
        service.update(id, request);
        return Response.status(Status.NO_CONTENT).build(); 
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"FUNCIONARIO"})
    public Response findAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        List<ClienteResponse> response = service.findAll(page, pageSize);
        long count = service.count();
        
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"FUNCIONARIO"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/email-cliente/{email}")
    @RolesAllowed({"FUNCIONARIO"})
    public Response findByEmail(@PathParam("email") String email) {
        return Response.ok(service.findByEmail(email)).build();
    }
    
    @GET
    @Path("/email/{id}")
    @RolesAllowed({"FUNCIONARIO"})
    public Response findEmailById(@PathParam("id") Long id) {
        return Response.ok(service.findEmailbyId(id)).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"FUNCIONARIO"})
    public Response count() {
        return Response.ok(service.count()).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"FUNCIONARIO"})
    public Response findByNome(@PathParam("nome") String nome) {
        List<ClienteResponse> response = service.findByNome(nome);
        long count = service.count(nome);
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    // --- NOVOS RECURSOS ---
    
    @PUT
    @Path("/resetar-senha/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response resetarSenha(@PathParam("id") Long id) {
        service.resetarSenha(id);
        return Response.status(Status.NO_CONTENT).build(); 
    }

    @POST
    @Path("/imagem/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"FUNCIONARIO"})
    public Response uploadImagemCliente(
            @PathParam("id") Long id,
            @RestForm("file") FileUpload file
    ){
        try {
            Long usuarioId = service.findUsuarioIdByClienteId(id);
            if (file == null) {
                throw ValidationException.of("file", "Arquivo é obrigatório.");
            }
            if (!ALLOWED_TYPES.contains(file.contentType())) {
                throw ValidationException.of("file", "Formato de imagem não permitido.");
            }
            if (file.size() > 10 * 1024 * 1024) {
                throw ValidationException.of("file", "Arquivo excede 10MB.");
            }

            InputStream is = Files.newInputStream(file.uploadedFile());
            String key = usuiarioStorageService.uploadImagem(usuarioId, is);

            return Response.ok("Upload realizado: " + key).build();

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            return Response.serverError().entity("Erro: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/imagem/{id}/url")
    @Produces({"image/png", "image/jpeg", "image/jpg", "image/gif"})
    @RolesAllowed({"FUNCIONARIO"})
    public Response getImageProduto(@PathParam("id") Long id) {
        try {
            Long usuarioId = service.findUsuarioIdByClienteId(id);
            InputStream url = usuiarioStorageService.getPrivateImage(usuarioId);
            return Response.ok(url).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("URL da imagem não encontrada ou erro: " + e.getMessage()).build();
        }
    }
}
