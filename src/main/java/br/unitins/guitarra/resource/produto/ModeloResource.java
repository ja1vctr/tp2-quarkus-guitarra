package br.unitins.guitarra.resource.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.ModeloRequest;
import br.unitins.guitarra.dto.produto.response.MarcaResponse;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;
import br.unitins.guitarra.service.produto.ModeloService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import jakarta.annotation.security.RolesAllowed;

@Path("/modelos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ModeloResource {

    @Inject
    ModeloService service;

    @POST
    @RolesAllowed({"FUNCIONARIO"})
    public Response create(ModeloRequest request) {
        ModeloResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response update(@PathParam("id") Long id, ModeloRequest request) {
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
    public Response findAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        List<ModeloResponse> response = service.findAll(page, pageSize);
        long count = service.count();
        
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<ModeloResponse> response = service.findByNome(nome);
        long count = service.countByNome(nome);

        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}/marcas")
    public List<MarcaResponse> findModelos(@PathParam("id") Long id) {
        return service.findMarcasByModelo(id);
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }
}
