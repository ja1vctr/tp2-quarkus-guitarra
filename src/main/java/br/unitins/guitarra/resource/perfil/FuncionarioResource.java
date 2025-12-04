package br.unitins.guitarra.resource.perfil;

import java.util.List;

import br.unitins.guitarra.dto.perfil.request.FuncionarioReduzidoRequest;
import br.unitins.guitarra.dto.perfil.request.FuncionarioRequest;
import br.unitins.guitarra.dto.perfil.response.FuncionarioResponse;
import br.unitins.guitarra.service.perfil.FuncionarioService;
import jakarta.enterprise.context.ApplicationScoped;
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

@ApplicationScoped
@Path("/funcionarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {
    @Inject
    FuncionarioService service;

    @POST
    public Response create(FuncionarioRequest request) {
        // Assumindo que o service.create retorna FuncionarioResponse
        FuncionarioResponse response = service.create(request);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, FuncionarioReduzidoRequest request) {
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
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        List<FuncionarioResponse> response = service.findAll(page, pageSize);
        long count = service.count();
        
        return Response.ok(response).header("X-Total-Count", count).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }
    
    @GET
    @Path("email/{id}")
    public Response findEmailById(@PathParam("id") Long id) {
        return Response.ok(service.findEmailbyId(id)).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }

    // --- NOVOS RECURSOS ---
    
    @PUT
    @Path("/alterar-senha")
    @Transactional
    public Response alterarSenha(FuncionarioRequest request, @QueryParam("novaSenha") String novaSenha) {
        service.alterarSenha(request, novaSenha);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/resetar-senha/{id}")
    @Transactional
    public Response resetarSenha(@PathParam("id") Long id) {
        service.resetarSenha(id);
        return Response.status(Status.NO_CONTENT).build(); 
    }

    @PUT
    @Path("/alterar-email")
    @Transactional
    public Response alterarEmail(FuncionarioRequest request, @QueryParam("novoEmail") String novoEmail) {
        service.alterarEmail(request, novoEmail);
        return Response.status(Status.NO_CONTENT).build();
    }
}