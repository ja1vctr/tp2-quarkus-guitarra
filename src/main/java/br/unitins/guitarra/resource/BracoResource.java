package br.unitins.guitarra.resource;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.BracoRequest;
import br.unitins.guitarra.dto.produto.response.BracoResponse;
import br.unitins.guitarra.service.produto.BracoService;
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

@Path("/bracos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BracoResource {

    @Inject
    BracoService service;

    @POST
    public Response create(BracoRequest request) {
        BracoResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, BracoRequest request) {
        service.update(id, request);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        
        List<BracoResponse> response = service.findAll(page, pageSize);
        long count = service.count();
        
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/formato/{formato}")
    public Response findByFormato(@PathParam("formato") String formato) {
        List<BracoResponse> response = service.findByFormato(formato);
        long count = service.count(formato);

        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }
}
