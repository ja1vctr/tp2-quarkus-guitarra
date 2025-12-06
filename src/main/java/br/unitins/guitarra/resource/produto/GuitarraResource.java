package br.unitins.guitarra.resource.produto;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.guitarra.dto.produto.request.GuitarraStatusRequest;
import br.unitins.guitarra.dto.produto.request.GuitarraRequest;
import br.unitins.guitarra.dto.produto.response.GuitarraResponse;
import br.unitins.guitarra.service.produto.GuitarraService;
import br.unitins.guitarra.service.storage.ProdutoStorageService;
import br.unitins.guitarra.validation.ValidationException;
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

@Path("/guitarras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GuitarraResource {

    @Inject
    GuitarraService service;

    @Inject
    ProdutoStorageService produtoStorageService;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/jpg",
            "image/gif"
    );

    @POST
    @RolesAllowed({"FUNCIONARIO"})
    public Response create(@Valid GuitarraRequest request) {
        GuitarraResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response update(@Valid GuitarraRequest request, @PathParam("id") Long id) {
        service.update(request, id);
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
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page, 
                            @QueryParam("pageSize") @DefaultValue("50") int pageSize) {
        List<GuitarraResponse> response = service.findAll(page, pageSize);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<GuitarraResponse> response = service.findByNome(nome);
        return Response.ok(response).build();
    }

    @GET
    @Path("/search/modelo/{modelo}")
    public Response findByModelo(@PathParam("modelo") String modelo) {
        List<GuitarraResponse> response = service.findByModelo(modelo);
        return Response.ok(response).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response updateStatus(@PathParam("id") Long id, @Valid GuitarraStatusRequest statusRequest) {
        GuitarraResponse response = service.updateStatus(id, statusRequest.status());
        return Response.ok(response).build();
    }

    // rotas de imagens
    
    @POST
    @Path("/imagem/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"FUNCIONARIO"})
    public Response uploadImagemProduto(
            @PathParam("id") Long id,
            @RestForm("file") FileUpload file
    ){
        try {
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
            String key = produtoStorageService.uploadImagem(id, is);

            return Response.ok("Upload realizado: " + key).build();

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            return Response.serverError().entity("Erro: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/imagem/{id}/url")
    @Produces({"image/png", "image/jpeg", "image/jpg", "image/gif"}) // Retorna apenas a URL como texto
    public Response getImageProduto(@PathParam("id") Long id) {
        try {
            InputStream url = produtoStorageService.getPrivateImage(id);
            return Response.ok(url).build();
        } catch (Exception e) {
            // Tratar erro se a chave nǜo existir ou o URL nǜo puder ser gerado
            return Response.status(Response.Status.NOT_FOUND).entity("URL da imagem nǜo encontrada ou erro: " + e.getMessage()).build();
        }
    }
}
