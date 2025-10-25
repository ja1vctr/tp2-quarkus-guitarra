package br.unitins.guitarra.validation;

import java.time.OffsetDateTime;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import br.unitins.guitarra.util.Error;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
@IfBuildProfile("prod")
public class UncaughtExceptionMapper implements ExceptionMapper<Throwable> {
 
  private static final Logger LOG = Logger.getLogger(UncaughtExceptionMapper.class);

  @ConfigProperty(name = "error.base-url")
  String baseUrl;
  
  @Context
  UriInfo uriInfo;

  @Override
  public Response toResponse(Throwable t) {
      // Log completo do erro (stacktrace) no servidor
      LOG.errorf(t, "Erro inesperado em %s", (uriInfo != null ? uriInfo.getRequestUri() : "<no-uri>"));

      Error error = new Error();
      error.type = baseUrl + "/errors/unexpected-error";
      error.title = "Erro inesperado";
      error.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
      error.detail = "Ocorreu um erro inesperado. Tente novamente mais tarde.";
      error.instance = (uriInfo != null ? uriInfo.getRequestUri().getPath() : null);
      error.timestamp = OffsetDateTime.now();
      
      // se você tiver um traceId de um filtro, injete aqui:
      // p.traceId = MDC.get("traceId"); // exemplo se usar SLF4J/MDC

      return Response.status(error.status)
              .type("application/problem+json")
              .entity(error)
              .build();
  }          

}
