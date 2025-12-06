package br.unitins.guitarra.resource.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.CaptadorRequest;
import br.unitins.guitarra.dto.produto.response.CaptadorResponse;
import br.unitins.guitarra.service.produto.CaptadorService;
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

@Path("/captadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CaptadorResource {

    @Inject
    CaptadorService service;

    @POST
    @RolesAllowed({"FUNCIONARIO"})
    public Response create(CaptadorRequest request) {
        CaptadorResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"FUNCIONARIO"})
    public Response update(@PathParam("id") Long id, CaptadorRequest request) {
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
        
        List<CaptadorResponse> response = service.findAll(page, pageSize);
        long count = service.count();
        
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/modelo/{modelo}")
    public Response findByModelo(@PathParam("modelo") String modelo) {
        List<CaptadorResponse> response = service.findByModelo(modelo);
        long count = service.countByModelo(modelo);

        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }
}
